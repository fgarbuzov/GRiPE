package simulator;

import java.io.FileNotFoundException;

import environment.Cell;

/**
 * class used by the GUI interface to simulate
 * @author n.r.zabet@gen.cam.ac.uk
 *
 */
public class SimulatorThread extends Thread{
	private Cell cell;
	private int steps;
	private double timeStep;
	private double time;
	public double elapsedTime;
	public double estimatedTime;
	private SimulatorGUI gui;
	private boolean initialised;
	private boolean isPaused;
	private int index;
	private String currentFile;
	private int ensembleSteps;
	
	public SimulatorThread(String currentFile, SimulatorGUI gui, int steps){
		super();
		this.currentFile = currentFile;
		this.gui = gui;
		this.steps = steps;
		initialiseSystem();
	}

	
	/**
	 * initialise internal parameters
	 */
	private void initialiseSystem(){
		initialised = false;
		try {
			cell =new Cell(currentFile,gui, false);
			initialised = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		double stopTime = cell.getTotalStopTime();
		timeStep = stopTime /steps;
		isPaused = false;
		time = 0;
		elapsedTime = 0;
		estimatedTime = 0;
		this.ensembleSteps = steps*cell.ip.ENSEMBLE_SIZE.value;
	}
	
	/**
	 * checks whether the cell was initialised
	 */
	public boolean isInitialised(){
		return initialised;
	}
	
	
	/**
	 * pauses the current simulation
	 */
	public void pauseSimulation(){
		isPaused = true;
	}
	
	/**
	 * resumes the current simulation
	 */
	public void resumeSimulation(){
		isPaused = false;
		this.notify();
	}
	
	
	/**
	 * restarts the current simulation
	 */
	public void restartSimulation(){
		isPaused = false;
		this.initialiseSystem();
		this.start();
	}
	
	/**
	 * run
	 */
    public void run() {
		if(cell.totalStopTime>0) {
			int ensemble;
			while(index<ensembleSteps && cell.cellTime<cell.totalStopTime && cell.ensemble < cell.ip.ENSEMBLE_SIZE.value) {
				ensemble = cell.ensemble;
				time+=timeStep;
				try {
					elapsedTime += cell.runInterval(time,elapsedTime);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				if(ensemble!=cell.ensemble) {
					time=0;
				}
				estimatedTime = steps*elapsedTime/(index+1) *cell.ip.ENSEMBLE_SIZE.value;
				index++;
				gui.updateProgress(index,elapsedTime,estimatedTime, cell.ip.ENSEMBLE_SIZE.value);

				if(index == ensembleSteps || cell.cellTime>=cell.totalStopTime && cell.ensemble >= cell.ip.ENSEMBLE_SIZE.value) {
					gui.finishedSimulations();
				}

				synchronized (this) {
					while (isPaused) {
						try {
							wait();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} else {
			try {
				while(cell.ensemble < cell.ip.ENSEMBLE_SIZE.value){
					cell.runUntilTSReached();
					if(cell.ensemble >= cell.ip.ENSEMBLE_SIZE.value){
						gui.finishedSimulations();
					}
					synchronized (this) {
						while (isPaused) {
							try {
								wait();
							} catch (Exception e) {
							}
						}
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}



    }
   
    
	
}
