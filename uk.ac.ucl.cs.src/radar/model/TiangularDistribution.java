package radar.model;

import java.util.List;

class TriangularDistribution extends Distribution {

	private double lower_, mode_, upper_;
	int N;
	public TriangularDistribution(){}
	public TriangularDistribution (double lower, double mode, double upper, int simulation){
		lower_ =lower;
		mode_ =mode;
		upper_ = upper;
		N =simulation;
	}
	double [] simulate (){
		return  triangularDistribution(lower_,mode_, upper_, N);
	}
	@Override
	public double[] simulate(Solution s) {
		return  triangularDistribution(lower_,mode_, upper_, N);
	}
	@Override
	public List<Node> addNodeToVariableGraph(Graph g, Model model,
			String qv_name) {
		return null;
	}
	@Override
	public List<Node> addNodeToDecisionGraph(Graph g, Model model,
			String qv_name) {
		return null;
	}
	
}