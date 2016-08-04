package radar.model;

import java.util.List;
import java.util.Map;

import prefuse.data.Graph;
import prefuse.data.Node;

class UniformDistribution extends Distribution {

	private double lower_, upper_;
	int N;
	public UniformDistribution (){
		
	}
	public UniformDistribution (double lower, double upper, int simulation){
		lower_ = lower;
		upper_=upper;
		N = simulation;
	}
	double [] simulate (){
		return  uniformDistribution(lower_,upper_, N);
	}
	@Override
	public double[] simulate(Alternative s) {
		isExpresionDistribution_ = true;
		return  uniformDistribution(lower_,upper_, N);
	}
	@Override
	public List<Node> addNodeToGraph(Graph g, Model model,String qv_name, Map<String, Node> cache) {
		return null;
	}
}
