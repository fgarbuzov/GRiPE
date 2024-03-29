package event;

import environment.Cell;
import utils.Constants;
import utils.Gillespie;

import java.io.Serializable;

/**
 * class that contains the event list
 *
 * @author n.r.zabet@gen.cam.ac.uk (original contributor)
 * @author fedor.garbuzov@mail.ioffe.ru (modified the code)
 */
public class EventList implements Serializable {

    private static final long serialVersionUID = 8623732338279526421L;
    public TFBindingEventQueue TFBindingEventQueue;
    public TFEventQueue TFRandomWalkEventQueue;
    public TFEventQueue TFRepressionEventQueue;


    public EventList(Cell n) {

        TFBindingEventQueue = new TFBindingEventQueue(n);

        // TF random walk event list 1D diffusion
        if (n.ip.EVENT_LIST_SUBGROUP_SIZE.value >= 0 && n.ip.EVENT_LIST_SUBGROUP_SIZE.value < n.dbp.length) {
            //TFRandomWalkEventQueue =  new TFRandomWalkEventQueueFRopt(n);
            n.printDebugInfo("Warning: clustered (optimal) First Reaction method is not implemented, " +
                    "simple First Reaction method will be used");
        } //else{
        TFRandomWalkEventQueue = new TFRandomWalkEventQueueFR(n);
        TFRepressionEventQueue = new TFRepressionEventQueueFR();
        //}
    }

    /**
     * returns the next TF binding event and removes it from the list
     */
    public ProteinEvent popNextTFBindingEvent() {
        return TFBindingEventQueue.pop();
    }

    /**
     * returns the next TF random walk event and removes it from the list
     */
    public ProteinEvent popNextTFRandomWalkEvent() {
        return (ProteinEvent) TFRandomWalkEventQueue.pop();
    }

    /**
     * FG
     * returns the next TF repression event and removes it from the list
     */
    public RepressionEvent popNextTFRepressionEvent() {
        return (RepressionEvent) TFRepressionEventQueue.pop();
    }

    /**
     * returns a number which encodes whether the next event is TF binding ...?
     */
    public int getNextEventType() {
        int result = Constants.NEXT_EVENT_IS_NONE;
        double nextEventTime = Double.MAX_VALUE;

        if (!TFBindingEventQueue.isEmpty() && nextEventTime > TFBindingEventQueue.peek().time) {
            nextEventTime = TFBindingEventQueue.peek().time;
            result = Constants.NEXT_EVENT_IS_TF_BINDING;
        }

        if (!TFRandomWalkEventQueue.isEmpty() && nextEventTime > TFRandomWalkEventQueue.peek().time) {
            nextEventTime = TFRandomWalkEventQueue.peek().time;
            result = Constants.NEXT_EVENT_IS_TF_RANDOM_WALK;
        }

        if (!TFRepressionEventQueue.isEmpty() && nextEventTime > TFRepressionEventQueue.peek().time) {
            //nextEventTime = TFRepressionEventQueue.peek().time;
            result = Constants.NEXT_EVENT_IS_TF_REPRESSION;
        }

        return result;
    }

    /**
     * returns the soonest event
     */
    public Event getNextEvent() {
        Event e = null;
        int nextEventType = getNextEventType();

        switch (nextEventType) {
            case Constants.NEXT_EVENT_IS_NONE:
                break;
            case Constants.NEXT_EVENT_IS_TF_BINDING:
                e = this.popNextTFBindingEvent();
                break;
            case Constants.NEXT_EVENT_IS_TF_RANDOM_WALK:
                e = this.popNextTFRandomWalkEvent();
                break;
            case Constants.NEXT_EVENT_IS_TF_REPRESSION:
                e = this.popNextTFRepressionEvent();
                break;
            default:
        }

        return e;
    }

    /**
     * checks whether there is any event left in the entire list
     */
    public boolean isEmpty() {
        return (TFBindingEventQueue == null || TFBindingEventQueue.isEmpty())
                && (TFRandomWalkEventQueue == null || TFRandomWalkEventQueue.isEmpty())
                && (TFRepressionEventQueue == null || TFRepressionEventQueue.isEmpty());
    }

    /**
     * computes the total number of events in the lists
     */
    public int size() {
        int result = 0;
        if (TFBindingEventQueue != null && !TFBindingEventQueue.isEmpty()) {
            result++;
        }
        if (TFRandomWalkEventQueue != null) {
            result += TFRandomWalkEventQueue.size();
        }
        return result;
    }

    /**
     * schedules the next TF binding event
     */
    public void scheduleNextTFBindingEvent(Cell n, double time) {
        // if no TF then halt
        double propensity = this.TFBindingEventQueue.proteinBindingPropensitySum;
        if (n.freeTFmoleculesTotal > 0 && propensity > 0) {

            //generate next reaction time
            double nextTime = Gillespie.computeNextReactionTime(
                    this.TFBindingEventQueue.proteinBindingPropensitySum, n.randomGenerator);

            //find next reaction (FG: randomly choose a TF specie to bind)
            int nextTFspecies = Gillespie.getNextReaction(
                    this.TFBindingEventQueue.proteinBindingPropensitySum * n.randomGenerator.nextDouble(),
                    this.TFBindingEventQueue.proteinBindingPropensity);

            if (nextTFspecies > Constants.NONE && nextTFspecies < n.TFspecies.length) {
                int TFID = n.getFreeTFmolecule(nextTFspecies);
                assert (TFID != Constants.NONE);
                int position = Constants.NONE;
                this.TFBindingEventQueue.add(new ProteinEvent(time + nextTime, TFID, position, true,
                        Constants.EVENT_TF_BINDING, false, propensity));
            }
        }
    }

    /**
     * schedules the next TF random walk or repression or derepression event
     */
    public void scheduleNextTFOnDNAEvent(Cell n, int moleculeID, double time) {
        double propensitySum, nextTime;
        // debug: check that there is no event for this molecule in the queue
        if (n.isInDebugMode() && TFRandomWalkEventQueue instanceof TFRandomWalkEventQueueFR) {
            for (ProteinEvent proteinEvent : ((TFRandomWalkEventQueueFR) this.TFRandomWalkEventQueue).randomWalkEvents) {
                if (proteinEvent.proteinID == moleculeID) {
                    n.stopSimulation("Error: attempted to schedule the event for the protein " + moleculeID
                            + " of type " + n.TFspecies[n.dbp[moleculeID].speciesID].name + ", but it is already scheduled.");
                }
            }
        }
        if (!n.TFspecies[n.dbp[moleculeID].speciesID].isImmobile) {
            ProteinEvent pe = (ProteinEvent) TFRandomWalkEventQueue.createNextEvent(n, moleculeID, time);
            RepressionEvent re = (RepressionEvent) TFRepressionEventQueue.createNextEvent(n, moleculeID, time);
            // decrease movement rate if the TF is repressing DNA
            if (n.dbp[moleculeID].isRepressingDNA()) {
                pe.propensity /= n.TFspecies[n.dbp[moleculeID].speciesID].repressionAttenuationFactor;
            }
            propensitySum = Math.min(pe.propensity + re.propensity, Double.MAX_VALUE);
            if (propensitySum > 0) {
                nextTime = Gillespie.computeNextReactionTime(propensitySum, n.randomGenerator);
                if (n.randomGenerator.nextDouble() * propensitySum < pe.propensity) {
                    pe.time = time + nextTime;
                    TFRandomWalkEventQueue.scheduleNextEvent(n, moleculeID, pe);
                } else {
                    re.time = time + nextTime;
                    TFRepressionEventQueue.scheduleNextEvent(n, moleculeID, re);
                }
            }
        }
    }

    /**
     * FG
     * schedules the next TF repression event
     */
    public void scheduleNextTFRepressionEvent(Cell n, int moleculeID, double time, boolean scheduleNextEvent) {
        RepressionEvent re = (RepressionEvent) TFRepressionEventQueue.createNextEvent(n, moleculeID, time);
        re.scheduleNextEvent = scheduleNextEvent;
        TFRepressionEventQueue.scheduleNextEvent(n, moleculeID, re);
    }

    /**
     * schedules the next TF random walk event
     */
    public void scheduleNextTFRandomWalkEvent(Cell n, int moleculeID, double time) {
        ProteinEvent pe = (ProteinEvent) TFRandomWalkEventQueue.createNextEvent(n, moleculeID, time);
        TFRandomWalkEventQueue.scheduleNextEvent(n, moleculeID, pe);
    }

}
