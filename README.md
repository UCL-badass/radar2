# Requirements and Architecture Decision AnalyseR

RADAR is a lightweight modelling language and decision analysis tool to support multi-objective decision under uncertainty in software requirements engineering and architectural design. RADAR analysis provides  useful feedback to requirement engineers and software designers about:

  - Which decisions are better than others in their model.
  - What objective values can be attained with with different designs.
  - What tradeoffs can be made between shortlisted solutions.
  - What parameter uncertainty may deserve additional data collection and analysis before making their decision.
  - What parameter uncertainty does not matter to their decision.

You can find details about RADAR on the [project webpage](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/)

# Papers

The modelling language and decision analysis support tool are described in the following paper which has been accepted for publication at ICSE17:

[Saheed A. Busari](http://www0.cs.ucl.ac.uk/staff/S.Busari/) and [Emmanuel Letier](http://letier.cs.ucl.ac.uk/): ["RADAR: A Lightwight Tool for Requirements and Architecture Decision Analysis"](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/radar_icse17.pdf), to appear in ICSE17, Buenos Aires, Argentina.

The scalability analysis of RADAR desision support tool is described in the paper below:

[Saheed A. Busari](http://www0.cs.ucl.ac.uk/staff/S.Busari/) and [Emmanuel Letier](http://letier.cs.ucl.ac.uk/): ["Scalability Analysis of the RADAR Decision Support Tool"](https://arxiv.org/pdf/1702.02977.pdf).

# Downloads

The RADAR tool is available and can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/RADAR.jar), and all model examples used in the paper can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/ModelExamples.zip).

We have also made the [source code](https://github.com/sbusari/RADAR) of the tool available in GitHub. The tool is implemented in Java and uses [ANTLR](http://www.antlr.org/) to generate model parser, generates diagrams in [DOT format](http://www.graphviz.org/Documentation/dotguide.pdf), and uses [Graphviz](http://graphviz.org/) (also available in [browser](http://webgraphviz.com/)) to visualise the diagrams.

# How to use RADAR

RADAR is a self-contained jar file. Simply download the file to your computer and double click to launch the application (needs JRE 1.7 or later versions). Once the application is launched successfully, a window appears with three panels as shown in figure 1:

  - **Model Board** where modellers can write their own models and also load existing models for review.
  - **Analysis Settings** for specifying parameters for model analysis. Examples of these parameters includes:
    - The number of Monte-Carlo simulation run.
    - Output directory where model analysis results are stored.
    - Information value objective to be used for computing the expected value of total and partial perfect information (evtpi and evppi)
    - Sub-graph objective for restricting the AND/OR graph to a single specified objective.
    - Some checkboxes used to indicate users' preferences on whether the tool should generate AND/OR dependency graph, decision dependency graph and the Pareto front.   
  - **Decisions** which displays all specified model decisions and their corresponding options once the model has been parsed successfully.
  
To analyse an existing RADAR model, the following steps can be followed:

1. Enable the model board by either clicking enable model board under the Radar menu or clicking the write model under the Action menu.
2. Open the RADAR file (we recommend starting with the first example below i.e. refactoring cost-benefit analysis) by simply clicking on the file menu and then click open to load the existing model on the model board as shown in figure 1. if successful, you will see the model displayed in the model board. At this point, users can edit the model and save changes by clicking on save under the file menu.
3. Go to the Action menu and click parse model to check that the specified model conforms to RADAR syntaxes defined in the paper. If not, an error message is displayed. If successful, you will be prompted to either continue with analysing the model or you could decide to further review the model and later analyse the model by clicking analyse model under the Action menu. Note that before parsing the model, you will be required to specify the output directory, which stores model analysis results.
4. If you click continue with model analysis, RADAR analyses the model as described in the paper, and the analysis results, such as the optimisation analysis, Pareto front (if checkbox is enabled) and information value analysis (if the information value objective is specified), are displayed in another window as shown in figure 2.

RADAR also saves the following model outputs in the specified output directory:

- The model analysis results in .csv and .out extensions.
- The AND/OR variable dependency graph (which helps to communicate and validate traceability links between technical software characteristics) and decisions dependency graph (which helps to visualise the dependencies between decisions and options, and also to view a potentially large design space in terms of a smaller set of decisions and options). These graphs are saved in DOT format and can be viewed using Graphviz (also available in browser).
- The Pareto front plot (in .PNG format) which is the set of optimal solutions that show the trade-offs between the multiple and conflicting objectives.

To analyse your own model, simply follow the same steps after having edited your model in the tool or using an external text editor.

For any question, email: {saheed.busari.13, e.letier}@ucl.ac.uk

# Examples

We have applied the tool to the following examples.

## Refactoring cost-benefit analysis

This is the small [refactoring example](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/refactoring_result.html) used in the paper to introduce the language and decision analysis method. The full model and analysis results can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/CBA.rdr).

## Plastic card fraud detection system

The [running example](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/fds_report.html) used in the paper to illustrate the language and analysis functions. The problem consists in selecting among alternative design for a fraud detection system so as to minimise financial loss due to fraud and minimise the number of fraud alerts to be investigated manually. The full model and analysis results can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/FDM.rdr).

## Document sharing security policy

The [document sharing example](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/bspdm_report.html) analyses  security policy decisions for an organisation concerned with leaks of confidential information. The analysis extends previous models developed by Tristan Caulfield and David Pym for a real organistion. The full model can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/BSPDM.rdr ).

## Emergency Response System

This  [emergency response example](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/sas_report.html ) illustrates the use of RADAR on an architecture decision problem used in previous publications (see ICSE 2013 for details). The design space for this model includes 6912 solutions and takes around 2 minutes to analyse.  The full model can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/SAS.rdr).

## Satellite Image Processing

The [satellite image example](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/ecs_report.html) was first introduced to illustrate the CBAM architecture decision method (see report for full references). We show here how to analyse this problem in RADAR and compare RADAR's approach to CBAM. The full model can be downloaded [here](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/ECS.rdr).

We have also applied RADAR on different [synthetic models](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/SyntheticModels.zip) to understand the scalability of the tool. The synthetic models are generated using a model generator that generates random syntactically valid RADAR models with a given number of objectives, decisions, number of options per decisions and minimum number of model variables. To create a synthetic model of a specific size, download the model generator jar file and run the command below:

**java -jar ./Downloads/SytheticModelGenerator.jar ./Downloads/SyntheticModel.properties**

In the command above, [SytheticModelGenerator.jar](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/SyntheticModelGenerator.jar) is the program that generates the synthetic model and [SyntheticModel.properties](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/SyntheticModel.properties) stores values for the RADAR model constructs, such as the number of objectives, decisions, number of options per decisions and minimum number of model variables. The file [SytheticModel.rdr](SyntheticModel.rdr) contains a synthetic RADAR model with decision dependency and has 2 objectives, minimum of 10 model variables, 4 decisions with their corresponding options ranging between 2 and 3.

The RADAR files can be opened and analysed in the tool following the steps outlined above.


# Instructions on running the souce code
The tool is implemented in Java and uses [ANTLR](http://www.antlr.org/) to generate model parser, generates diagrams in DOT format, and uses [Graphviz ](http://graphviz.org/)  to visualise the diagrams. To continue development on RADAR, follow the instructions below:

  - Clone or download the project source code from the GIT repository
  - Import the project into your IDE of preference e.g. Eclipse Luna.
  - Add all the libraries listed below to the project build path.
  - Build the project 
  - Right click on the file "RADAR_GUI.java" and click "Run AS" to run java application.
  
The following are the libraries to be added to your project. They can be found within the downloaded project in the folder "uk.ac.ucl.cs.lib" :
  - antlr-4.5.1-complete.jar, which is used to generate model parser. 
  - commons-lang3-3.4.jar, which contains some string manipulating functions
  - commons-math3-3.6.1.jar, which containes some of the maths functions used for statistical and probabilistc analysis.
  - jcommander-1.7.jar, used to build the command line tool
  - opencsv-3.1.jar, for managing  csv files.
  - jfreechart-1.0.19.lar and jcommon-1.0.23.jar for generating 2D plots 
  - orsoncharts-1.5.jar for generating 3D plots.

# Roadmap

Radar is an early prototype that we are constantly improving. Our current development goals include:
  - Applying RADAR to additional examples.
  - Extending the language to support decisions with non-mutually exclusive options.
  - Various improvements to the user interface: syntax highlighting, better error messages, and fully integrated graphic generation.
  - Provide fully documented API for use of Radar by external tools.
  - Facilitating generation of decision analysis report by embedding RADAR model fragments and analysis results within markdown documents.


