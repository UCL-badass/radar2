package radar.model;

interface ModelVisitorElement {

	void accept(ModelVisitor visitor, Model m);
}
