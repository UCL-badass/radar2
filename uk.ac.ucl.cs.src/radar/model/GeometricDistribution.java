package radar.model;

import radar.exception.ParameterDistributionException;


class GeometricDistribution extends Distribution {
	private double prob_ ;
	int N;
	public GeometricDistribution (){
		
	}
	public GeometricDistribution (double prob,int simulation){
		prob_ = prob;
		N = simulation;
	}
	double [] simulate (){
		return  geometricDistribution(prob_, N);
	}
	
	@Override
	public double[] simulate(Solution s) {
		return  geometricDistribution(prob_, N);
	}
	@Override
	public void  getCyclicDependentVariables(Model m) {
	}
	@Override
	public double getParamExpressionValue(Model m)
			throws ParameterDistributionException {
		throw new RuntimeException ("Geometric distribution cannot be used as an argument for another distribution.");
	}
}
