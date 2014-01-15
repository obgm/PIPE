package pipe.petrinet.adapters.modelAdapter;

import pipe.models.component.Connectable;
import pipe.models.component.Transition;
import pipe.petrinet.adapters.model.AdaptedConnectable;
import pipe.petrinet.adapters.model.AdaptedTransition;
import pipe.petrinet.adapters.model.PositionGraphics;
import pipe.petrinet.adapters.model.Point;
import pipe.petrinet.adapters.utils.ConnectableUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.HashMap;
import java.util.Map;

public class TransitionAdapter extends XmlAdapter<AdaptedTransition, Transition>{
    private final Map<String, Transition> transitions;

    /**
     * Empty contructor needed for marshelling. Since the method to marshell does not actually
     * use these fields it's ok to initialise them as empty/null.
     */
    public TransitionAdapter() {
        transitions = new HashMap<String, Transition>();
    }

    public TransitionAdapter(Map<String, Transition> transitions) {

        this.transitions = transitions;
    }

    @Override
    public Transition unmarshal(AdaptedTransition adaptedTransition) throws Exception {
        AdaptedConnectable.NameDetails nameDetails = adaptedTransition.getName();
        Transition transition = new Transition(adaptedTransition.getId(), nameDetails.getName());
        ConnectableUtils.setConntactableNameOffset(transition, adaptedTransition);
        ConnectableUtils.setConnectablePosition(transition, adaptedTransition);
        transition.setAngle(adaptedTransition.getAngle());
        transition.setPriority(adaptedTransition.getPriority());
        transition.setRateExpr(adaptedTransition.getRate());
        transition.setTimed(adaptedTransition.getTimed());
        transition.setInfiniteServer(adaptedTransition.getInfiniteServer());
        transitions.put(transition.getId(), transition);
        return transition;
    }

    @Override
    public AdaptedTransition marshal(Transition transition) throws Exception {
        AdaptedTransition adaptedTransition = new AdaptedTransition();
        ConnectableUtils.setAdaptedName(transition, adaptedTransition);

        adaptedTransition.setId(transition.getId());
        ConnectableUtils.setPosition(transition, adaptedTransition);
        adaptedTransition.setPriority(transition.getPriority());
        adaptedTransition.setAngle(transition.getAngle());
        adaptedTransition.setRate(transition.getRateExpr());
        adaptedTransition.setInfiniteServer(transition.isInfiniteServer());
        adaptedTransition.setTimed(transition.isTimed());
        return adaptedTransition;
    }
}