package utilsGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import objects.InputParameters;

/**
 * tabbed panel with TF parameters
 * @author n.r.zabet@gen.cam.ac.uk
 *
 */
public class TFParameters  extends JPanel{

	private static final long serialVersionUID = 1L;


	//TF PARAMETERS
	public LabelledFileChooser TF_FILE;
	public LabelledFileChooser TF_COOPERATIVITY_FILE;
	public LabelledFileChooser TS_FILE;

	//TF_RANDOM PARAMETERS
	public LabelledCheckBox RANDOM_TFS;
	public LabelledInteger TF_DBD_LENGTH_MIN;
	public LabelledInteger TF_DBD_LENGTH_MAX;
	public LabelledInteger TF_SPECIES_COUNT;
	public LabelledInteger TF_COPY_NUMBER_MIN;
	public LabelledInteger TF_COPY_NUMBER_MAX;
	public LabelledInteger TF_SIZE_LEFT;
	public LabelledInteger TF_SIZE_RIGHT;
	public LabelledDouble TF_ES;
	public LabelledDouble TF_ASSOC_RATE;
	public LabelledDouble TF_PREBOUND_PROPORTION;
	public LabelledCheckBox TF_PREBOUND_TO_HIGHEST_AFFINITY;
	public LabelledCheckBox TF_READ_IN_BOTH_DIRECTIONS;
	public LabelledCheckBox SLIDING_AND_HOPPING_AFFECTS_TF_ASSOC_RATE;
	// FG: repression
	public LabelledInteger TF_REPR_LEN_LEFT;
	public LabelledInteger TF_REPR_LEN_RIGHT;
	public LabelledDouble TF_REPRESSION_RATE;
	public LabelledDouble TF_DEREPRESSION_ATTENUATION_FACTOR;


	public TFParameters(InputParameters ip){
		this.setMaximumSize(new Dimension(GUIconstants.SIMULATION_PARAMETERS_SIZE_WIDTH,GUIconstants.SIMULATION_PARAMETERS_SIZE_HIGHT));
		this.setLayout(new FlowLayout());
		JPanel componentsStack = new JPanel(new GridLayout(0, 1, GUIconstants.GRID_HGAP, GUIconstants.GRID_VGAP));
		componentsStack.setMaximumSize(new Dimension(GUIconstants.SIMULATION_PARAMETERS_SIZE_WIDTH, GUIconstants.SIMULATION_PARAMETERS_SIZE_HIGHT));

		JLabel label1, label2, label3;
		label1 = new JLabel(GUIconstants.SIMULATION_AREA_TF_LOAD_PARAMATERS);
		label2 = new JLabel(GUIconstants.SIMULATION_AREA_TF_RANDOM_PARAMATERS);
		label3 = new JLabel(GUIconstants.SIMULATION_AREA_TF_GENERAL_PARAMATERS);
		label1.setForeground(Color.LIGHT_GRAY);
		label2.setForeground(Color.LIGHT_GRAY);
		label3.setForeground(Color.LIGHT_GRAY);

		//TF PARAMETERS
		TF_FILE = new LabelledFileChooser(ip.TF_FILE.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_FILE.description,ip.TF_FILE.value, true, true);
		TS_FILE = new LabelledFileChooser(ip.TS_FILE.label,GUIconstants.TEXTAREA_WIDTH,ip.TS_FILE.description,ip.TS_FILE.value, true, true);
		TF_COOPERATIVITY_FILE = new LabelledFileChooser(ip.TF_COOPERATIVITY_FILE.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_COOPERATIVITY_FILE.description,ip.TF_COOPERATIVITY_FILE.value, true, true);

		TF_READ_IN_BOTH_DIRECTIONS = new LabelledCheckBox(ip.TF_READ_IN_BOTH_DIRECTIONS.label, ip.TF_READ_IN_BOTH_DIRECTIONS.description, ip.TF_READ_IN_BOTH_DIRECTIONS.value);
		SLIDING_AND_HOPPING_AFFECTS_TF_ASSOC_RATE = new LabelledCheckBox(ip.SLIDING_AND_HOPPING_AFFECTS_TF_ASSOC_RATE.label, ip.SLIDING_AND_HOPPING_AFFECTS_TF_ASSOC_RATE.description, ip.SLIDING_AND_HOPPING_AFFECTS_TF_ASSOC_RATE.value);

		//TF_RANDOM PARAMETERS
		RANDOM_TFS = new LabelledCheckBox("Randomly generate TFs?", "When checked, the default random TF parameters become editable and the TF files are deleted.", false);
		TF_DBD_LENGTH_MIN = new LabelledInteger(ip.TF_DBD_LENGTH_MIN.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_DBD_LENGTH_MIN.description, ip.TF_DBD_LENGTH_MIN.value);
		TF_DBD_LENGTH_MAX = new LabelledInteger(ip.TF_DBD_LENGTH_MAX.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_DBD_LENGTH_MAX.description, ip.TF_DBD_LENGTH_MAX.value);
		TF_SPECIES_COUNT = new LabelledInteger(ip.TF_SPECIES_COUNT.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_SPECIES_COUNT.description, ip.TF_SPECIES_COUNT.value);
		TF_COPY_NUMBER_MIN = new LabelledInteger(ip.TF_COPY_NUMBER_MIN.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_COPY_NUMBER_MIN.description, ip.TF_COPY_NUMBER_MIN.value);
		TF_COPY_NUMBER_MAX = new LabelledInteger(ip.TF_COPY_NUMBER_MAX.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_COPY_NUMBER_MAX.description, ip.TF_COPY_NUMBER_MAX.value);
		TF_SIZE_LEFT = new LabelledInteger(ip.TF_SIZE_LEFT.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_SIZE_LEFT.description, ip.TF_SIZE_LEFT.value);
		TF_SIZE_RIGHT = new LabelledInteger(ip.TF_SIZE_RIGHT.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_SIZE_RIGHT.description, ip.TF_SIZE_RIGHT.value);
		TF_ES = new LabelledDouble(ip.TF_ES.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_ES.description,ip.TF_ES.value);
		TF_ASSOC_RATE = new LabelledDouble(ip.TF_ASSOC_RATE.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_ASSOC_RATE.description,ip.TF_ASSOC_RATE.value);
		TF_PREBOUND_PROPORTION = new LabelledDouble(ip.TF_PREBOUND_PROPORTION.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_PREBOUND_PROPORTION.description,ip.TF_PREBOUND_PROPORTION.value);
		TF_PREBOUND_TO_HIGHEST_AFFINITY = new LabelledCheckBox(ip.TF_PREBOUND_TO_HIGHEST_AFFINITY.label, ip.TF_PREBOUND_TO_HIGHEST_AFFINITY.description, ip.TF_PREBOUND_TO_HIGHEST_AFFINITY.value);

		TF_REPR_LEN_LEFT = new LabelledInteger(ip.TF_REPR_LEN_LEFT.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_REPR_LEN_LEFT.description,ip.TF_REPR_LEN_LEFT.value);
		TF_REPR_LEN_RIGHT = new LabelledInteger(ip.TF_REPR_LEN_RIGHT.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_REPR_LEN_RIGHT.description,ip.TF_REPR_LEN_RIGHT.value);
		TF_REPRESSION_RATE = new LabelledDouble(ip.TF_REPRESSION_RATE.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_REPRESSION_RATE.description,ip.TF_REPRESSION_RATE.value);
		TF_DEREPRESSION_ATTENUATION_FACTOR = new LabelledDouble(ip.TF_DEREPRESSION_ATTENUATION_FACTOR.label,GUIconstants.TEXTAREA_WIDTH,ip.TF_DEREPRESSION_ATTENUATION_FACTOR.description,ip.TF_DEREPRESSION_ATTENUATION_FACTOR.value);

		resetLabelsWidth();

		setRandomParInputsStatus(RANDOM_TFS.getValue());

		RANDOM_TFS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setRandomParInputsStatus(RANDOM_TFS.getValue());
				TF_FILE.setEnable(!RANDOM_TFS.getValue());
				TS_FILE.setEnable(!RANDOM_TFS.getValue());
				if (RANDOM_TFS.getValue()) {
					TF_FILE.setValue("");
					TS_FILE.setValue("");
				} else {
					TF_FILE.setValue(ip.TF_FILE.value);
					TS_FILE.setValue(ip.TS_FILE.value);
				}
			}
		});


		//TF PARAMETERS
		componentsStack.add(label1);
		componentsStack.add(TF_FILE);
		componentsStack.add(TS_FILE);
		componentsStack.add(TF_COOPERATIVITY_FILE);

		componentsStack.add(label3);
		componentsStack.add(TF_READ_IN_BOTH_DIRECTIONS);
		componentsStack.add(SLIDING_AND_HOPPING_AFFECTS_TF_ASSOC_RATE);

		//TF_RANDOM PARAMETERS
		componentsStack.add(label2);
		componentsStack.add(RANDOM_TFS);
		componentsStack.add(TF_SPECIES_COUNT);
		componentsStack.add(TF_COPY_NUMBER_MIN);
		componentsStack.add(TF_COPY_NUMBER_MAX);
		componentsStack.add(TF_DBD_LENGTH_MIN);
		componentsStack.add(TF_DBD_LENGTH_MAX);
		componentsStack.add(TF_SIZE_LEFT);
		componentsStack.add(TF_SIZE_RIGHT);
		componentsStack.add(TF_ES);
		componentsStack.add(TF_ASSOC_RATE);
		componentsStack.add(TF_PREBOUND_PROPORTION);
		componentsStack.add(TF_PREBOUND_TO_HIGHEST_AFFINITY);
		componentsStack.add(TF_REPRESSION_RATE);
		componentsStack.add(TF_DEREPRESSION_ATTENUATION_FACTOR);
		componentsStack.add(TF_REPR_LEN_LEFT);
		componentsStack.add(TF_REPR_LEN_RIGHT);

		this.add(componentsStack);
	}

	private void setRandomParInputsStatus(boolean status) {
		TF_DBD_LENGTH_MIN.setEditable(status);
		TF_DBD_LENGTH_MAX.setEditable(status);
		TF_SPECIES_COUNT.setEditable(status);
		TF_COPY_NUMBER_MIN.setEditable(status);
		TF_COPY_NUMBER_MAX.setEditable(status);
		TF_SIZE_LEFT.setEditable(status);
		TF_SIZE_RIGHT.setEditable(status);
		TF_ES.setEditable(status);
		TF_ASSOC_RATE.setEditable(status);
		TF_PREBOUND_PROPORTION.setEditable(status);
		TF_PREBOUND_TO_HIGHEST_AFFINITY.setEnable(status);
		TF_REPR_LEN_LEFT.setEditable(status);
		TF_REPR_LEN_RIGHT.setEditable(status);
		TF_REPRESSION_RATE.setEditable(status);
		TF_DEREPRESSION_ATTENUATION_FACTOR.setEditable(status);
	}

	/**
	 * resets the labels width
	 */
	private void resetLabelsWidth(){

		//SIMULATION PARAMATERS
		int max = TF_FILE.getLabelWidth();
		if(TF_COOPERATIVITY_FILE.getLabelWidth() > max){
			max = TF_COOPERATIVITY_FILE.getLabelWidth();
		}
		if(TS_FILE.getLabelWidth() > max){
			max = TS_FILE.getLabelWidth();
		}

		//TF_RANDOM PARAMETERS
		if(TF_DBD_LENGTH_MIN.getLabelWidth() > max){
			max = TF_DBD_LENGTH_MIN.getLabelWidth();
		}
		if(TF_DBD_LENGTH_MAX.getLabelWidth() > max){
			max = TF_DBD_LENGTH_MAX.getLabelWidth();
		}
		if(TF_SPECIES_COUNT.getLabelWidth() > max){
			max = TF_SPECIES_COUNT.getLabelWidth();
		}
		if(TF_COPY_NUMBER_MIN.getLabelWidth() > max){
			max = TF_COPY_NUMBER_MIN.getLabelWidth();
		}
		if(TF_COPY_NUMBER_MAX.getLabelWidth() > max){
			max = TF_COPY_NUMBER_MAX.getLabelWidth();
		}
		if(TF_SIZE_LEFT.getLabelWidth() > max){
			max = TF_SIZE_LEFT.getLabelWidth();
		}
		if(TF_SIZE_RIGHT.getLabelWidth() > max){
			max = TF_SIZE_RIGHT.getLabelWidth();
		}
		if(TF_ES.getLabelWidth() > max){
			max = TF_ES.getLabelWidth();
		}
		if(TF_ASSOC_RATE.getLabelWidth() > max){
			max = TF_ASSOC_RATE.getLabelWidth();
		}
		if(TF_PREBOUND_PROPORTION.getLabelWidth() > max){
			max = TF_PREBOUND_PROPORTION.getLabelWidth();
		}
		if(TF_REPR_LEN_LEFT.getLabelWidth() > max){
			max = TF_REPR_LEN_LEFT.getLabelWidth();
		}
		if(TF_REPR_LEN_RIGHT.getLabelWidth() > max){
			max = TF_REPR_LEN_RIGHT.getLabelWidth();
		}
		if(TF_REPRESSION_RATE.getLabelWidth() > max){
			max = TF_REPRESSION_RATE.getLabelWidth();
		}
		if(TF_DEREPRESSION_ATTENUATION_FACTOR.getLabelWidth() > max){
			max = TF_DEREPRESSION_ATTENUATION_FACTOR.getLabelWidth();
		}

		//TF PARAMETERS
		TF_FILE.setLabelWidth(max);
		TF_COOPERATIVITY_FILE.setLabelWidth(max);
		TS_FILE.setLabelWidth(max);

		//TF_RANDOM PARAMETERS
		TF_DBD_LENGTH_MIN.setLabelWidth(max);
		TF_DBD_LENGTH_MAX.setLabelWidth(max);
		TF_SPECIES_COUNT.setLabelWidth(max);
		TF_COPY_NUMBER_MIN.setLabelWidth(max);
		TF_COPY_NUMBER_MAX.setLabelWidth(max);
		TF_SIZE_LEFT.setLabelWidth(max);
		TF_SIZE_RIGHT.setLabelWidth(max);
		TF_ES.setLabelWidth(max);
		TF_ASSOC_RATE.setLabelWidth(max);
		TF_PREBOUND_PROPORTION.setLabelWidth(max);

		TF_REPR_LEN_LEFT.setLabelWidth(max);
		TF_REPR_LEN_RIGHT.setLabelWidth(max);
		TF_REPRESSION_RATE.setLabelWidth(max);
		TF_DEREPRESSION_ATTENUATION_FACTOR.setLabelWidth(max);
	}
}
