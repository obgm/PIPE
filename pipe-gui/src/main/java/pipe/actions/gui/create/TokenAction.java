package pipe.actions.gui.create;

import pipe.controllers.PetriNetController;
import pipe.controllers.PlaceController;
import pipe.historyActions.AddPetriNetObject;
import pipe.historyActions.ChangePlaceTokens;
import pipe.historyActions.MultipleEdit;
import pipe.models.component.Connectable;
import pipe.models.component.place.Place;
import pipe.models.component.token.Token;

import javax.swing.event.UndoableEditEvent;
import javax.swing.undo.AbstractUndoableEdit;
import javax.swing.undo.UndoManager;
import javax.swing.undo.UndoableEdit;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public abstract class TokenAction extends CreateAction {

    public TokenAction(String name, String tooltip, int key, int modifiers) {
        super(name, tooltip, key, modifiers);
    }

    /**
     * Subclasses should perform their relevant action on the token e.g. add/delete
     * @param placeController
     * @param token
     */
    protected abstract void performTokenAction(PlaceController placeController, Token token);

    @Override
    public void doAction(MouseEvent event, PetriNetController petriNetController) {
        // Do nothing unless clicked a connectable
    }

    @Override
    public void doConnectableAction(Connectable connectable, PetriNetController petriNetController) {
        //TODO: Maybe a method, connectable.containsTokens()
        if (connectable instanceof Place) {
            Place place = (Place) connectable;
            Token token = petriNetController.getSelectedToken();
            performTokenAction(petriNetController.getPlaceController(place), token);
        }
    }

    /**
     *
     * Sets the place to contain counts.
     *
     * Creates a new history edit
     *
     * @param placeController
     * @param counts
     */
    protected void setTokenCounts(PlaceController placeController, Map<Token, Integer> counts) {
        placeController.setTokenCounts(counts);
    }
}