package radar.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;


public class ModelConstructor {

	int simulation;
	public ModelConstructor(int simulationRun){
		simulation =simulationRun;
	}
	public  Model createNewModel (){
		return new Model();
	}
	void addModelSubGraphObjective (Model model, String obj_name, String subGraphObj){
		if (subGraphObj != null && !StringUtils.isEmpty(subGraphObj)){
			Objective subGraphObjective = new Objective();
			List<Objective> objectives = model.getObjectives();
			for (int i=0; i < objectives.size(); i ++){
				if (objectives.get(i).getLabel().equals(subGraphObj.trim())){
					subGraphObjective= objectives.get(i);
				}
			}
			model.setSubGraphObjective(subGraphObjective);
		}
		
	} 
	void addModelInfoValueObjective (Model model, String obj_name, String infoValueObj){
		if (infoValueObj != null && !StringUtils.isEmpty(infoValueObj)){
			Objective infoValueObjective = new Objective();
			List<Objective> objectives = model.getObjectives();
			for (int i=0; i < objectives.size(); i ++){
				if (objectives.get(i).getLabel().equals(infoValueObj.trim())){
					infoValueObjective= objectives.get(i);
				}
			}
			model.setInfoValueObjective(infoValueObjective);
		}
		
	}
	void addModelObjective (Model model, String obj_name, Objective objective){
		model.addObjective(obj_name, objective);
	}
	 void addModelQualityVariable(Model model, String qv_name, QualityVariable qualityVariable){
		model.addQualityVariables(qv_name, qualityVariable);
	}
	public  Model addQualityVariablesToModel (Model model, Map<String, QualityVariable> qvlist){
		for (Map.Entry<String, QualityVariable> entry: qvlist.entrySet()){
			QualityVariable modelQv = entry.getValue();
			addModelQualityVariable(model,entry.getKey(),modelQv);
		}
		return model;
	}
	public  Model addDecisionsToModel (Model model, Map<String, Decision> decision_list){
		model.setDecisions(decision_list);
		return model;
	}
	public  Model addModelName (Model model, String modelName){
		model.setModelName(modelName);
		return model;
	}
	public  Model addObjectivesToModel (Model model, Map<String, Value> obj_definition,Map<String, Objective> obj_list, Map<String, QualityVariable> qvlist,String infoValueObjective, String subGraphObjective){
		for (Map.Entry<String, Objective> entry: obj_list.entrySet()){
			Value obj = obj_definition.get(entry.getKey());
			Objective modelObj = entry.getValue();
			if (obj.getStatistic() instanceof Expectation){
				Expectation exp = (Expectation)obj.getStatistic();
				Identifier id = (Identifier)exp.getObjExpression();
				//add Objective QualityVariable ReferedTo
				modelObj.setQualityVariable(qvlist.get(id.getID()));
				modelObj.setStatistic(exp);
			}else if (obj.getStatistic() instanceof BooleanProbability){
				BooleanProbability bool_prob = (BooleanProbability)obj.getStatistic();
				Identifier id = (Identifier)bool_prob.getObjExpression();
				//add Objective Quality Variable ReferedTo
				modelObj.setQualityVariable(qvlist.get(id.getID()));
				modelObj.setStatistic(bool_prob);
			}else if (obj.getStatistic() instanceof Probability){
				Probability prob = (Probability)obj.getStatistic();
				modelObj.setStatistic(prob);
				BinaryExpression binaryExpr = (BinaryExpression)prob.getObjExpression();
				if (!(binaryExpr.getLeftExpression() instanceof Identifier)){
					throw new RuntimeException ("Left operand of a comparison expression must be a quality variable");
				}else{
					//add Objective Quality Variable ReferedTo
					modelObj.setQualityVariable(qvlist.get(((Identifier)binaryExpr.getLeftExpression()).getID()));
				}
			}
			addModelObjective(model,modelObj.getLabel(),modelObj );
			addModelInfoValueObjective ( model, modelObj.getLabel(), infoValueObjective);
			addModelSubGraphObjective (model, modelObj.getLabel(), subGraphObjective);
		}
		return model;
	}
	public  QualityVariable createNewQualityVariable(){
		QualityVariable qv = new QualityVariable ();
		return qv;
	}
	public  QualityVariable addQualityVariableExpression (QualityVariable qv, String qv_name, Value qv_def, QualityVariable parent){
		qv.setLabel(qv_name.toString());
		Expression qv_expr = (Expression)qv_def.getExpression();
		qv_expr.setParent(parent);
		qv.setDefinition(qv_expr);
		return qv;
	}
	
	// helpsm to display options even for deterministic options of the and/or graph e.g cost[new]
	public void setModelParameterLabels (Model m){
		List<String> paramNames = m.getParameters();
		for (int i =0 ; i < m.getParameters().size(); i++){
			QualityVariable qv = m.getQualityVariables().get(paramNames.get(i));			
			if (qv.getDefinition() instanceof Parameter  ){
				Parameter value = (Parameter)qv.getDefinition();
				value.setLabel(qv.getLabel());
			}else if (qv.getDefinition() instanceof OR_Refinement){
				Map<String, AND_Refinement> optionsExpr = ((OR_Refinement)qv.getDefinition()).getDefinition();
				for (Map.Entry<String, AND_Refinement> entry: optionsExpr.entrySet()){
					if (entry.getValue().getDefinition() instanceof Parameter){
						Parameter value = (Parameter)entry.getValue().getDefinition();
						value.setLabel(qv.getLabel() + "[" +entry.getKey() + "]");
					}
				}
			}
		}
	}
	public void addQualityVariableParameter (Model m, QualityVariable qv){
		if (qv.getDefinition() instanceof Parameter  ){
			m.addParameters(qv.getLabel());
		}else if (qv.getDefinition() instanceof OR_Refinement){
			Map<String, AND_Refinement> optionsExpr = ((OR_Refinement)qv.getDefinition()).getDefinition();
			for (Map.Entry<String, AND_Refinement> entry: optionsExpr.entrySet()){
				if (entry.getValue().getDefinition() instanceof Parameter ){
					m.addParameters(qv.getLabel());
				}
			}
		}
	}
	public void addInformationValueParameters1 (Model m, QualityVariable qv){
		if (qv.getDefinition() instanceof Parameter &&  !(((Parameter)qv.getDefinition()).getDistribution() instanceof DeterministicDistribution) ){
			m.addParameters(qv.getLabel());
		}else if (qv.getDefinition() instanceof OR_Refinement){
			Map<String, AND_Refinement> optionsExpr = ((OR_Refinement)qv.getDefinition()).getDefinition();
			for (Map.Entry<String, AND_Refinement> entry: optionsExpr.entrySet()){
				if (entry.getValue().getDefinition() instanceof Parameter && !(((Parameter)entry.getValue().getDefinition()).getDistribution() instanceof DeterministicDistribution)){
					m.addParameters(qv.getLabel());
				}
			}
		}
	}
	public  OR_Refinement createNewOr_Refinement (){
		OR_Refinement or_refinement = new OR_Refinement ();
		return or_refinement;
	}
	public  Value addOR_RefinementDefinition (OR_Refinement or_ref, String option_name, Value option_def, QualityVariable and_Ref_Parent){
		AND_Refinement and_ref = new AND_Refinement();
		if (and_Ref_Parent != null){
			and_ref.setParent(and_Ref_Parent);
		}
		and_ref.addDefinition(option_def.getArithmeticExpression());
		or_ref.addDefinition(option_name, and_ref);
		return new Value(or_ref);
	}
	public  Decision createNewDecision(){
		Decision decision = new Decision ();
		return decision;
	}
	public  Decision addDecisionDefinition (Decision d, String decision_name, String option){
		d.setDecisionLabel(decision_name);
		d.addOption(option);
		return d;
	}
	public  Objective createNewObjective (String obj_name,String optimisationDirection){
		Objective obj = new Objective ();
		obj.setLabel(obj_name);
		if (optimisationDirection.endsWith("Min")){
			obj.setIsMinimisation(true);
		}
		return obj;
	}
	public  Value addObjectiveExpectation (String referredQV){
		Expectation exp = new Expectation();
		Identifier qv_id =new Identifier();
		qv_id.setID(referredQV);
		exp.setObjExpression(qv_id);
		return new Value (exp);
	}
	public  Value addObjectiveBooleanProbablity (String referredQV){
		BooleanProbability boop = new BooleanProbability();
		Identifier qv_id =new Identifier();
		qv_id.setID(referredQV);
		boop.setObjExpression(qv_id);
		return new Value (boop);
	}
	public  Value addObjectiveProbablity (Value referredQV){
		Probability p = new Probability();
		// get the comparison expression from Value referredQV
		p.setObjExpression(referredQV.getArithmeticExpression());
		BinaryExpression binexpr = (BinaryExpression)referredQV.getArithmeticExpression();
		String comparator = binexpr.getBinaryOperator().getBinaryOperatorValue();
		p.setComparator(comparator);
		if(binexpr.getRightExpression() instanceof Number){
			p.xIsAScaler(true);
			p.setScalerValue(((Number)binexpr.getRightExpression()).getValue());
		}else if (binexpr.getRightExpression() instanceof Identifier){
			p.xIsAScaler(false);
		}
		return new Value (p);
	}
	public  Value addNumberExpression (String number){
		Number numeric = new Number();
		Double decimalValue = null;
		try{
			decimalValue =Double.parseDouble(number);
		}catch (Exception e){
			
		}
		numeric.setValue(decimalValue);
		return new Value(numeric);
	}
	private void validateDistributionArgumentCount (Value distribution, List<Value> distributionArguments){
		if (distributionArguments.size() != 0 && distribution.toString().equals(ParameterDistribution.RANDOM.toString()) ){
			 throw new RuntimeException ("Only random distribution can have empty parameter list. Check the documentation for details.");
		}else if (distributionArguments.size() != 1 && (distribution.toString().equals(ParameterDistribution.EXPONENTIAL.toString())
				|| distribution.toString().equals(ParameterDistribution.GEOMETRIC.toString()) || distribution.toString().equals(ParameterDistribution.DETERMINISTIC.toString() ))){
			throw new RuntimeException ("Only exponential, determistic and geometric distributions can have one parameter list. Check the documentation for details.");
		}else if (distributionArguments.size() != 2 && (distribution.toString().equals(ParameterDistribution.NORMAL.toString())
				|| distribution.toString().equals(ParameterDistribution.BINOMIAL.toString()) ||distribution.toString().equals(ParameterDistribution.UNIFORM.toString())
				|| distribution.toString().equals(ParameterDistribution.NORMAL_CI.toString())) ){
			throw new RuntimeException ("Only normal, uniform , normal_CI and binomial distributions can have two parameter list. Check the documentation for details.");
		}else if (distributionArguments.size() != 3 && distribution.toString().equals(ParameterDistribution.TRIANGULAR.toString())){
			throw new RuntimeException ("Distibution error: only triangualr distribution can have three parameter list. Check the documentation for details.");
		}else{}
	}
	private void checkDistributionArgumentIsNumber (Value distribution, List<Value> distributionArguments){
		if (distributionArguments != null && distributionArguments.size() >0){
			for (int i =0; i < distributionArguments.size(); i ++){
				try{
					Double.parseDouble(distributionArguments.get(i).toString());
				}catch (Exception e ){
					throw new RuntimeException ("Arguments parameter for distribution " + distribution.toString() + " must be a number");
				}
			}
		}
	}
	public  Value addDistribution (Value distribution, List<Value> distributionArguments){
		Parameter param =new Parameter ();
		validateDistributionArgumentCount(distribution,distributionArguments);
		checkDistributionArgumentIsNumber(distribution,distributionArguments);
		if (distribution.toString().equals(ParameterDistribution.NORMAL.toString())){
			double mean = distributionArguments.get(0).convertToDouble();
			double sd = distributionArguments.get(1).convertToDouble();
			NormalDistribution nd = new NormalDistribution(mean,sd,simulation);
			param.setDistribution(nd);
			param.setDefinition(nd);
		}else if (distribution.toString().equals(ParameterDistribution.TRIANGULAR.toString())){
			double lower = distributionArguments.get(0).convertToDouble();
			double mode = distributionArguments.get(1).convertToDouble();
			double upper = distributionArguments.get(2).convertToDouble();
			TriangularDistribution tr = new TriangularDistribution(lower, mode, upper,simulation);
			param.setDistribution(tr);
			param.setDefinition(tr);
		}else if (distribution.toString().equals(ParameterDistribution.NORMAL_CI.toString())){
			double a = distributionArguments.get(0).convertToDouble();
			double b = distributionArguments.get(1).convertToDouble();
			NormalCIDistribution ncid = new NormalCIDistribution(a,b,simulation);
			param.setDistribution(ncid);
			param.setDefinition(ncid);
		}else if (distribution.toString().equals(ParameterDistribution.UNIFORM.toString())){
			double lower = distributionArguments.get(0).convertToDouble();
			double upper = distributionArguments.get(1).convertToDouble();
			UniformDistribution und = new UniformDistribution(lower,upper,simulation);
			param.setDistribution(und);
			param.setDefinition(und);
		}else if (distribution.toString().equals(ParameterDistribution.DETERMINISTIC.toString())){
			double value = distributionArguments.get(0).convertToDouble();
			DeterministicDistribution dt = new DeterministicDistribution(value,simulation);
			param.setDistribution(dt);
			param.setDefinition(dt);
		}
		else if (distribution.toString().equals(ParameterDistribution.EXPONENTIAL.toString())){
			double mean = distributionArguments.get(0).convertToDouble();
			ExponentialDistribution expd = new ExponentialDistribution(mean,simulation);
			param.setDistribution(expd);
			param.setDefinition(expd);
		}else if (distribution.toString().equals(ParameterDistribution.GEOMETRIC.toString())){
			double prob = distributionArguments.get(0).convertToDouble();
			GeometricDistribution geod = new GeometricDistribution(prob,simulation);
			param.setDistribution(geod);
			param.setDefinition(geod);
		}
		else if (distribution.toString().equals(ParameterDistribution.BINOMIAL.toString())){
			double trial = distributionArguments.get(0).convertToDouble();
			double prob = distributionArguments.get(1).convertToDouble();
			BinomialDistribution bid = new BinomialDistribution((int)trial,prob, simulation);
			param.setDistribution(bid);
			param.setDistribution(bid);
		}else if (distribution.toString().equals(ParameterDistribution.RANDOM.toString())){;
			RandomDistribution rd = new RandomDistribution(simulation);
			param.setDistribution(rd);
			param.setDistribution(rd);
		}
		return new Value(param);
	}
	public  Value addIdentifierExpression (String id, QualityVariable parent){
		Identifier var_id = new Identifier();
		QualityVariable linkedQualityVariable = new QualityVariable();
		linkedQualityVariable.setLabel(id);
		var_id.setLinkedQualityVariable(linkedQualityVariable);
		var_id.setParent(parent);
		var_id.setID(id);
		return new Value(var_id);
	}
	private  BinaryOperator getBinaryOperator (String operator){
		BinaryOperator bop = null;
		switch(operator){
			case "+": bop = BinaryOperator.ADD; break;
			case "-": bop = BinaryOperator.SUB; break;
			case "/": bop = BinaryOperator.DIV; break;
			case "*": bop = BinaryOperator.MUL; break;
			case "||": bop = BinaryOperator.OR; break;
			case "&&": bop = BinaryOperator.AND; break;
			case "<": bop = BinaryOperator.LESS; break;
			case ">": bop = BinaryOperator.GRET; break;
			case "<=": bop = BinaryOperator.LEQ; break;
			case ">=": bop = BinaryOperator.GEQ; break;
		}
		return bop;
	}
	private  UnaryOperator getUnaryOperator (String operator){
		UnaryOperator uop = null;
		switch(operator){
			case "!": uop = UnaryOperator.NOT; break;
			case "-": uop = UnaryOperator.NEG; break;
			case "+": uop = UnaryOperator.POS; break;
		}
		return uop;
	}
	public  Value addComparatorExpression (Value left, Value right, String op){
		BinaryExpression bexpr = new BinaryExpression();
		if (!(left.getArithmeticExpression() instanceof Identifier)){
			throw new RuntimeException ("Left operand of a comparison expression must be a quality variable");
		}
		bexpr.setLeftExpression(left.getArithmeticExpression());
		bexpr.setRightExpression(right.getArithmeticExpression());
		BinaryOperator bop = getBinaryOperator(op);
		bexpr.setBinaryOperator(bop);
		return new Value (bexpr);
	}
	public  Value addBinaryExpression (Value left, Value right, String op, QualityVariable parent){
		BinaryExpression bexpr = new BinaryExpression();
		bexpr.setLeftExpression(left.getArithmeticExpression());
		bexpr.setRightExpression(right.getArithmeticExpression());
		BinaryOperator bop = getBinaryOperator(op);
		bexpr.setBinaryOperator(bop);
		bexpr.setParent(parent);
		return new Value (bexpr);
	}
	public  Value addUnaryExpression (Value expr, String op, QualityVariable parent){
		UnaryExpression uexpr = new UnaryExpression();
		uexpr.setExpression(expr.getArithmeticExpression());
		UnaryOperator uop = getUnaryOperator(op);
		uexpr.setUnaryOperator(uop);
		uexpr.setParent(parent);
		return new Value (uexpr);
	}
	public  boolean doesDistributionArgumentHasExpr2(Value arguement){
		boolean distributionArgumentHasExpr = false;
		if (arguement.getExpression().getClass().equals(BinaryExpression.class)){
			distributionArgumentHasExpr = true;
		}
		return distributionArgumentHasExpr;
	}
	public  boolean doesDistributionArgumentHasExpr (String distributionArgument, String operator){
		boolean distributionArgumentHasExpr = false;
		String operators = "["+ operator +"]";
        Pattern opeartorPattern = Pattern.compile(operators);
        boolean operatorExist = opeartorPattern.matcher(distributionArgument).find();
        if (operatorExist == true){
        	distributionArgumentHasExpr =true;
		}
        return distributionArgumentHasExpr;
	}
	public  Value updateDistributionArguementValue (String computedDistributionExpression){
		Number numeric = new Number();
		Double exprValue = null;
		try{
			exprValue =Double.parseDouble(computedDistributionExpression);
		}catch (Exception e){
			throw new RuntimeException ("Parameter distribution expression cannot be cast to a number.");
		}
		numeric.setValue(exprValue);
		return new Value(numeric);
	}
	public  double findExponent (Value base, Value power){
		Double exprBase = null; 
		Double exprPower =null; 
		try{
			// check if it is a unary expression and get the number ffrom it.
			if (base.getExpression() instanceof UnaryExpression){
				Number base_no =  (Number)((UnaryExpression) base.getArithmeticExpression()).getExpression();
				UnaryOperator op = ((UnaryExpression) base.getExpression()).getUnaryOperator();
				if ( op.equals(UnaryOperator.NOT)){
					exprBase = 1 - base_no.getValue();
				}else if (op.equals(UnaryOperator.NEG)){
					exprBase = (-1 * base_no.getValue());
				}else if (op.equals(UnaryOperator.POS)){
					exprBase = base_no.getValue();
				}
			}else{
				exprBase = Double.parseDouble( base.toString());
			}
			if (power.getArithmeticExpression() instanceof UnaryExpression){
				Number power_no = (Number)((UnaryExpression) power.getArithmeticExpression()).getExpression();
				UnaryOperator op = ((UnaryExpression) power.getArithmeticExpression()).getUnaryOperator();
				if ( op.equals(UnaryOperator.NOT)){
					exprPower = 1 - power_no.getValue();
				}else if (op.equals(UnaryOperator.NEG)){
					exprPower = (-1 * power_no.getValue());
				}else if (op.equals(UnaryOperator.POS)){
					exprPower = power_no.getValue();
				}
			}else{
				exprPower = Double.parseDouble( power.toString());
			}
			
		}catch(Exception e){
			throw new RuntimeException ("Incorrect syntax for exponential arithmetic_expr. Ensure numbers are separated from key words and other string literals. \n  Check documentation for details.");
		}
		double exponent = Math.pow(exprBase,exprPower); 
		return exponent;
	}


}
