package pipe.dsl;

import org.junit.Before;
import org.junit.Test;
import pipe.models.component.Connectable;
import pipe.models.component.arc.Arc;
import pipe.models.component.arc.InboundNormalArc;
import pipe.models.component.place.Place;
import pipe.models.component.rate.RateParameter;
import pipe.models.component.token.Token;
import pipe.models.component.transition.Transition;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class ANormalArcTest {
    private Map<String, Token> tokens;

    private Map<String, Place> places;

    private Map<String, RateParameter> rateParameters;

    private Map<String, Transition> transitions;

    @Before
    public void setUp() {
        tokens = new HashMap<>();
        places = new HashMap<>();
        transitions = new HashMap<>();
        rateParameters = new HashMap<>();
    }

    @Test
    public void createsArcWithSourceAndTarget() {
        places.put("P0", new Place("P0", "P0"));
        transitions.put("T1", new Transition("T1", "T1"));
        Arc<? extends Connectable, ? extends Connectable> arc =
                ANormalArc.withSource("P0").andTarget("T1").create(tokens, places, transitions, rateParameters);

        Arc<? extends Connectable, ? extends Connectable> expected =
                new InboundNormalArc(places.get("P0"), transitions.get("T1"), new HashMap<Token, String>());
        assertEquals(expected, arc);
    }

    @Test
    public void createsArcSingleToken() {
        tokens.put("Red", new Token("Red", Color.RED));
        places.put("P0", new Place("P0", "P0"));
        transitions.put("T1", new Transition("T1", "T1"));
        Arc<? extends Connectable, ? extends Connectable> arc =
                ANormalArc.withSource("P0").andTarget("T1").with("4", "Red").tokens().create(tokens, places, transitions,
                        rateParameters);

        HashMap<Token, String> tokenWeights = new HashMap<>();
        tokenWeights.put(tokens.get("Red"), "4");
        Arc<? extends Connectable, ? extends Connectable> expected =
                new InboundNormalArc(places.get("P0"), transitions.get("T1"), tokenWeights);

        assertEquals(expected, arc);
    }

    @Test
    public void createsArcWithMultipleTokens() {
        tokens.put("Red", new Token("Red", Color.RED));
        tokens.put("Default", new Token("Default", Color.BLACK));
        places.put("P0", new Place("P0", "P0"));
        transitions.put("T1", new Transition("T1", "T1"));
        Arc<? extends Connectable, ? extends Connectable> arc =
                ANormalArc.withSource("P0").andTarget("T1").with("4", "Red").tokens().and("1", "Default").token().create(tokens,
                        places, transitions,
                        rateParameters);

        HashMap<Token, String> tokenWeights = new HashMap<>();
        tokenWeights.put(tokens.get("Red"), "4");
        tokenWeights.put(tokens.get("Default"), "1");
        Arc<? extends Connectable, ? extends Connectable> expected =
                new InboundNormalArc(places.get("P0"), transitions.get("T1"), tokenWeights);

        assertEquals(expected, arc);
    }

}

