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

[Saheed A. Busari](http://www0.cs.ucl.ac.uk/staff/S.Busari/) and [Emmanule Letier] (http://letier.cs.ucl.ac.uk/): ["RADAR: A Lightwight Tool for Requirements and Architecture Decision Analysis"](http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/radar_icse17.pdf), to appear in ICSE17, Buenos Aires, Argentina.

The scalability analysis of RADAR desision support tool is described in the paper below:

[Saheed A. Busari](http://www0.cs.ucl.ac.uk/staff/S.Busari/) and [Emmanule Letier] (http://letier.cs.ucl.ac.uk/): ["Scalability Analysis of the RADAR Decision Support Tool"], Available Online: https://arxiv.org/pdf/1702.02977.pdf.

# Downloads
The RADAR tool is available and can be downloaded [here] (http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/RADAR.jar), and all model examples used in the paper can be downloaded [here] (http://www0.cs.ucl.ac.uk/staff/S.Busari/RADAR/ModelExamples.zip).

We have also made the [source code](https://github.com/sbusari/RADAR) of the tool available in GitHub. The tool is implemented in Java and uses [ANTLR](http://www.antlr.org/) to generate model parser, generates diagrams in [DOT format](http://www.graphviz.org/Documentation/dotguide.pdf), and uses [Graphviz](http://graphviz.org/) (also available in [browser](http://webgraphviz.com/)) to visualise the diagrams.

# How to use RADAR
RADAR is a self-contained jar file. Simply download the file to your computer and double click to launch the application (needs JRE 1.7 or later versions). Once the application is launched successfully, a window appears with three panels as shown in figure 1:
  - Model Board where modellers can write their own models and also load existing models for review.
  - Analysis Settings for specifying parameters for model analysis. Examples of these parameters includes:
   - The number of Monte-Carlo simulation run.
   - Output directory where model analysis results are stored.
   - Information value objective to be used for computing the expected value of total and partial perfect information (evtpi and evppi)
   - Sub-graph objective for restricting the AND/OR graph to a single specified objective
   - Some checkboxes used to indicate users' preferences on whether the tool should generate AND/OR dependency graph, decision dependency graph and the Pareto front.
  - **Decisions** which displays all specified model decisions and their corresponding options once the model has been parsed successfully.

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



