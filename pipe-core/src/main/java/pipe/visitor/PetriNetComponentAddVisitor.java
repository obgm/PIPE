package pipe.visitor;

import pipe.models.PetriNet;
import pipe.models.component.*;

public class PetriNetComponentAddVisitor implements PetriNetComponentVisitor {
    private PetriNet petriNet;

    public PetriNetComponentAddVisitor(PetriNet petriNet) {
        this.petriNet = petriNet;
    }


    @Override
    public <T extends Connectable, S extends Connectable> void visit(Arc<S, T> arc) {
        petriNet.addArc(arc);
    }

    @Override
    public void visit(Place place) {
        petriNet.addPlace(place);
    }

    @Override
    public void visit(Transition transition) {
        petriNet.addTransition(transition);
    }

    @Override
    public void visit(Token token) {
        petriNet.addToken(token);
    }
//
    //    @Override
    //    public void visit(RateParameter parameter) {
    //        petriNet.addRate(parameter);
    //    }
    //
    //    @Override
    //    public void visit(StateGroup group) {
    //        petriNet.addStateGroup(group);
    //    }

    @Override
    public void visit(Annotation annotation) {
        petriNet.addAnnotaiton(annotation);
    }

    @Override
    public void visit(ArcPoint arcPoint) {

    }
}
