import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;

/**
 * Controller class.
 *
 * @author Albert Hu
 */
public final class NNCalcController1 implements NNCalcController {

	/**
	 * Model object.
	 */
	private final NNCalcModel model;

	/**
	 * View object.
	 */
	private final NNCalcView view;

	/**
	 * Useful constants.
	 */
	private static final NaturalNumber TWO = new NaturalNumber2(2), INT_LIMIT = new NaturalNumber2(Integer.MAX_VALUE);

	/**
	 * Updates this.view to display this.model, and to allow only operations that
	 * are legal given this.model.
	 *
	 * @param model the model
	 * @param view  the view
	 * @ensures [view has been updated to be consistent with model]
	 */
	private static void updateViewToMatchModel(NNCalcModel model, NNCalcView view) {

		// Get Model
		NaturalNumber top = model.top();
		NaturalNumber bottom = model.bottom();

		// Subtract Legality
		if (top.compareTo(bottom) < 0) {
			view.updateSubtractAllowed(false);
		} else {
			view.updateSubtractAllowed(true);
		}

		// Divide Legality
		if (bottom.isZero()) {
			view.updateDivideAllowed(false);
		} else {
			view.updateDivideAllowed(true);
		}

		// Power Legality
		if (bottom.compareTo(INT_LIMIT) > 0) {
			view.updatePowerAllowed(false);
		} else {
			view.updatePowerAllowed(true);
		}

		// Root Legality
		if (bottom.compareTo(TWO) < 0 || bottom.compareTo(INT_LIMIT) > 0) {
			view.updateRootAllowed(false);
		} else {
			view.updateRootAllowed(true);
		}

		// Updates View
		view.updateTopDisplay(top);
		view.updateBottomDisplay(bottom);

	}

	/**
	 * Constructor.
	 *
	 * @param model model to connect to
	 * @param view  view to connect to
	 */
	public NNCalcController1(NNCalcModel model, NNCalcView view) {
		this.model = model;
		this.view = view;
		updateViewToMatchModel(model, view);
	}

	@Override
	public void processClearEvent() {
		/*
		 * Get alias to bottom from model
		 */
		NaturalNumber bottom = this.model.bottom();
		/*
		 * Update model in response to this event
		 */
		bottom.clear();
		/*
		 * Update view to reflect changes in model
		 */
		updateViewToMatchModel(this.model, this.view);
	}

	@Override
	public void processSwapEvent() {
		/*
		 * Get aliases to top and bottom from model
		 */
		NaturalNumber top = this.model.top();
		NaturalNumber bottom = this.model.bottom();
		/*
		 * Update model in response to this event
		 */
		NaturalNumber temp = top.newInstance();
		temp.transferFrom(top);
		top.transferFrom(bottom);
		bottom.transferFrom(temp);
		/*
		 * Update view to reflect changes in model
		 */
		updateViewToMatchModel(this.model, this.view);
	}

	@Override
	public void processEnterEvent() {

		// Update Model
		NaturalNumber bottom = this.model.bottom();
		this.model.top().copyFrom(bottom);
		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

	@Override
	public void processAddEvent() {

		// Update Model
		NaturalNumber top = this.model.top();
		this.model.bottom().add(top);
		this.model.top().setFromInt(0);
		// Update View
		updateViewToMatchModel(this.model, this.view);
	}

	@Override
	public void processSubtractEvent() {

		// Update Model
		NaturalNumber top = this.model.top();
		NaturalNumber bottom = this.model.bottom();
		top.subtract(bottom);
		this.model.bottom().copyFrom(top);
		this.model.top().setFromInt(0);
		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

	@Override
	public void processMultiplyEvent() {

		// Update Model
		NaturalNumber top = this.model.top();
		this.model.bottom().multiply(top);
		this.model.top().setFromInt(0);
		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

	@Override
	public void processDivideEvent() {

		// Update Model
		NaturalNumber top = this.model.top();
		NaturalNumber bottom = this.model.bottom();
		NaturalNumber rem = top.divide(bottom);
		this.model.bottom().copyFrom(top);
		this.model.top().copyFrom(rem);

		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

	@Override
	public void processPowerEvent() {

		// Update Model
		NaturalNumber top = this.model.top();
		NaturalNumber bottom = this.model.bottom();
		top.power(bottom.toInt());
		this.model.bottom().copyFrom(top);
		this.model.top().setFromInt(0);
		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

	@Override
	public void processRootEvent() {

		// Update Model
		NaturalNumber top = this.model.top();
		NaturalNumber bottom = this.model.bottom();
		top.root(bottom.toInt());
		this.model.bottom().copyFrom(top);
		this.model.top().setFromInt(0);
		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

	@Override
	public void processAddNewDigitEvent(int digit) {

		// Update Model
		this.model.bottom().multiplyBy10(digit);
		// Update View
		updateViewToMatchModel(this.model, this.view);

	}

}
