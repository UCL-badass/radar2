package radar.model;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class QualityVariable extends ArithmeticExpression implements ModelVisitorElement {

	private String label_;
	private Expression definition_;
	private Map<Solution, double[]> simData_;
	public QualityVariable(){
		simData_ = new LinkedHashMap<Solution, double[]>();
	}
	public void setLabel (String label){
		label_ = label;
	}
	public String getLabel (){
		return label_;
	}
	public void setDefinition (Expression def){
		definition_ = def;
	}
	public Expression getDefinition (){
		return definition_;
	}
	public double[] getSimData(Solution s) {
		return simulate(s);
	}
	public double[][] getSimData(List<Solution> s) {
		double [][] result = new double [s.size()][];
		for (int i =0 ; i < s.size(); i ++){
			result[i] = getSimData (s.get(i));
		}
		return result;
	}
	double[][] simulate(List<Solution> s) {
		double [][] result = new double [s.size()][];
		for (int i =0 ; i < s.size(); i ++){
			result[i] = simulate(s.get(i));
		}
		return result;
	}
	public double [] simulate (Solution s){
		return definition_.simulate(s);
	}
	public Map<Solution, double[]> getSimData(){
		return simData_;
	}
	public void setSimData(Map<Solution, double[]> simdata){
		simData_ =simdata;
	}
	@Override
	List<QualityVariable> getQualityVariable() {
		List<QualityVariable> result = new ArrayList<QualityVariable>();
		//if (! (this instanceof Parameter)){ // we do not want to show links to the option names
			result.add(this);
		//}
		return result;
	}
	
	public int hashCode(){
		return this.getLabel().hashCode();
	}
	@Override
	public void accept(ModelVisitor visitor, Model m) {
		// definition for the option may be null
		if (definition_ == null){ // can only be null if it was populated partially for an arithmtetic expr 
			QualityVariable qv = m.getQualityVariables().get(label_);
			qv.getDefinition().accept(visitor,m);
		}else{
			definition_.accept(visitor,m);
		}
		visitor.visit(this);
	}
	@Override
	public QualityVariable getParent() {
		return null;
	}





	
}
