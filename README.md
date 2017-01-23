## asCTL-Model-Checker.
The goal of this project is to design and implement a model checker for an action
and state-based logic *asCTL* interpreted over transition systems. Additionally, the model checker
must support fairness in the sense that any constraints can be added to restrict the set of paths
considered for verification. If a model checker computes that the logical formula doesn’t hold for
a certain model, the system must generate the trace where the formula indeed
doesn’t hold.

##Logic *asCTL*
LTL and CTL are well-known types of logic. However, neither of the logics is suitable for making statements about the sequences of
actions carried out before reaching a particular state. One example of a property
we may want to be able to express is *it is always the case that after action α or
β occurs the system is in a state where p holds*. Or *the system will eventually
reach a goal state by only performing legal actions*.

Full task description (including formal definition of *asCTL* logic) is in "/doc/TaskDescription.pdf".

##How to encode logic formulas and models in a parser-friendly form?
One of the core components of the system is the "Formula Parser" which essentially converts raw logic formula to the grammar tree.
The rules of how to encode valid logic formulas and valid models in Json format are listed and explained in "/doc/Input_Specification.pdf". 

##Outcome
As a result of this project, a model checker for *asCTL* was designed and fully
implemented. Given a transition system and *asCTL* formula, a model checker computes whether
the formula is satisfied or not over the transition system. If it is not satisfied, the correspondent
trace is returned to prove it. Besides, constraints can be added so that a model checker restricts
the set of paths for verification. Additionally, a couple of improvements were implemented: a
model validation and a formula conversion algorithm (conversion from non-ENF form to ENF).
The developed model checker was properly tested by using models of various topologies and
formulas with various combinations of logical operators.

The report with a very detailed explanation of how the solution is designed and implemented can be found in "/doc/Report.pdf".
The implementation is written in Java and uses ["Gradle"](https://gradle.org/) build automation system. Instructions of how to build and run the program are given in the report as well.  

