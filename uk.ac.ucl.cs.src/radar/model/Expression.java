package radar.model;


 abstract class Expression implements ModelVisitorElement {

	public abstract double [] simulate (Solution s);
	//public abstract QualityVariable getParent(); 
	QualityVariable parent_;
	public QualityVariable getParent() {
		return parent_;
	}
	public void setParent(QualityVariable parent) {
		parent_ = parent;
	}
	public abstract void accept (ModelVisitor visitor, Model m);
	public Expression() {}
	
}
