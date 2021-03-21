package mechfite.ui;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public final class MechFiteGUI extends Application {
	
	public static final double REF_RATIO = 1920.0 / Screen.getPrimary().
			getBounds().getWidth();
	
	private static ObservableList<Node> rootNodes;
	
	private static Button menuBtn;
	
	private static Button pauseBtn;
	
	private static TextField mech1Name;
	
	private static TextField mech2Name;
	
	private static ProgressBar mech1HP;
	
	private static ProgressBar mech2HP;
	
	private static ProgressBar mech1SB;
	
	private static ProgressBar mech2SB;
	
	private static Button mech1FPS;
	
	private static Button mech2FPS;
	
	private static Button timerBtn;
	
	private static MechGroup playerGroup;
	
	private static MechGroup aiGroup;
	
	private static Mech playerMech;
	
	private static Mech aiMech;
	
	public static void main( String[] args ) {
		launch( args );
	}
	
	@Override
	public void start( Stage stage ) throws Exception {
		
		Font.loadFont( MechFiteGUI.class.getClass().
				getResourceAsStream( "/fonts/ObliviousFont.ttf" ), 1.0f );
		
		Pane mfRoot = new StackPane();
		rootNodes = mfRoot.getChildren();
		
		Pane tPane = new VBox();
		rootNodes.add( tPane );
		
		Scene scene = new Scene( mfRoot );
		scene.getStylesheets().setAll( MechFiteGUI.class.
				getResource( "/css/main.css" ).toExternalForm() );
		
		Pane titleHeader = new HBox();
		titleHeader.setStyle( "-fx-alignment: center;"
				+ "-fx-padding: " + UIDimensions.TB_PAD + " 0 0 0" );
		
		Button titleBtn = new Button( "MECHFITE" );
		titleBtn.getStyleClass().add( "title-button" );
		titleBtn.setPrefSize( UIDimensions.TITLE_WIDTH,
				UIDimensions.TITLE_HEIGHT );
		
		menuBtn = new Button();
		menuBtn.getStyleClass().add( "options-button" );
		menuBtn.setOnMouseClicked( e -> {
			
			Rectangle2D rec = Screen.getPrimary().getBounds();
			double width = rec.getWidth();
			double height = rec.getHeight();
			
			Button bg = new Button();
			bg.setStyle( "-fx-background-color: white;"
					+ "-fx-opacity: 0.5" );
			bg.setPrefSize( width, height );
			rootNodes.add( bg );
		});
		menuBtn.setPrefSize( UIDimensions.OPTIONS_WIDTH,
				UIDimensions.TITLE_HEIGHT );
		
		pauseBtn = new Button();
		pauseBtn.getStyleClass().add( "options-button" );
		pauseBtn.setOnMouseClicked( e -> clickPause() );
		pauseBtn.setPrefSize( UIDimensions.OPTIONS_WIDTH,
				UIDimensions.TITLE_HEIGHT );
		pauseBtn.setDisable( true );
		
		titleHeader.getChildren().setAll( menuBtn, titleBtn, pauseBtn );
		
		Pane mechBoxes = new HBox();
		mechBoxes.setStyle( "-fx-padding: 50 0 0 150" );
		
		Pane mech1Box = new VBox();
		mech1Name = new TextField( "PLAYER" );
		mech1Name.setPrefSize( UIDimensions.MECH_GROUP_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		mech1Name.getStyleClass().add( "mech-name" );
		mech1Name.setAlignment( Pos.CENTER );
		
		Pane hpsb1Box = new HBox();
		
		mech1HP = new ProgressBar();
		mech1HP.setPrefSize( UIDimensions.HPSB_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		mech1HP.getStyleClass().add( "hp-bar" );
		mech1HP.setProgress( 1.0 );
		
		mech1SB = new ProgressBar();
		mech1SB.setPrefSize( UIDimensions.HPSB_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		mech1SB.getStyleClass().add( "sb-bar" );
		mech1SB.setProgress( 1.0 );
		
		hpsb1Box.getChildren().setAll( mech1HP, mech1SB );
		
		mech1FPS = new Button();
		mech1FPS.getStyleClass().add( "fight-points-button" );
		mech1FPS.setPrefSize( UIDimensions.MECH_GROUP_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		
		mech1Box.getChildren().setAll( mech1Name, hpsb1Box, mech1FPS );
		
		Pane timePane = new HBox();
		timerBtn = new Button( "--:--" );
		timerBtn.setPrefSize( UIDimensions.TIMER_WIDTH,
				UIDimensions.TIMER_HEIGHT );
		timerBtn.getStyleClass().add( "timer-button" );
		timePane.getChildren().setAll( timerBtn );
		
		timePane.setStyle( "-fx-padding: 0 " +
				UIDimensions.TIMER_PAD + " 0 " +
				UIDimensions.TIMER_PAD );
		
		Pane mech2Box = new VBox();
		mech2Name = new TextField( "AI" );
		mech2Name.setPrefSize( UIDimensions.MECH_GROUP_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		mech2Name.getStyleClass().add( "mech-name" );
		mech2Name.setAlignment( Pos.CENTER );
		
		Pane hpsb2Box = new HBox();
		
		mech2HP = new ProgressBar();
		mech2HP.setPrefSize( UIDimensions.HPSB_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		mech2HP.getStyleClass().add( "hp-bar" );
		mech2HP.setProgress( 1.0 );
		
		mech2SB = new ProgressBar();
		mech2SB.setPrefSize( UIDimensions.HPSB_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		mech2SB.getStyleClass().add( "sb-bar" );
		mech2SB.setProgress( 1.0 );
		
		hpsb2Box.getChildren().setAll( mech2HP, mech2SB );
		
		mech2FPS = new Button();
		mech2FPS.getStyleClass().add( "fight-points-button" );
		mech2FPS.setPrefSize( UIDimensions.MECH_GROUP_WIDTH,
				UIDimensions.MECH_COMPONENT_HEIGHT );
		
		mech2Box.getChildren().setAll( mech2Name, hpsb2Box, mech2FPS );
		
		mechBoxes.getChildren().setAll( mech1Box, timePane, mech2Box );
		
		tPane.getChildren().setAll( titleHeader, mechBoxes );
		
		stage.setScene( scene );
		stage.setFullScreen( true );
		stage.show();
	}
	
	private void clickOptionsMenu() {
		
	}
	
	private void clickPause() {
		
	}
	
	private void clickResume() {
		
	}
	
	private void clickForfeit() {
		
	}
	
	private void clickAttack() {
		
	}
	
	private void clickSpecialMove() {
		
	}
	
	private void clickSkip() {
		
	}
	
	private void clickBlock() {
		
	}
	
	private void clickParry() {
		
	}
	
	private void clickCounter() {
		
	}
	
	private void clickSMCounter() {
		
	}
	
	private void clickAbsorb() {
		
	}
	
	private void playAIOffensive() {
		
	}
	
	private void clickShields() {
		
	}
	
	private void clickStopTime() {
		
	}
	
	private void clickHealthBoost() {
		
	}
	
	private void clickImmunity() {
		
	}
	
	public static final class Mech {
		
		private static final int DEFAULT_HEALTH = 500;
		private static final int DEFAULT_SBAR = 50;
		private DoubleProperty hp;
		private DoubleProperty sbar;
		private IntegerProperty fightPts;
		
		public Mech() {
			hp = new SimpleDoubleProperty( DEFAULT_HEALTH );
			sbar = new SimpleDoubleProperty( DEFAULT_SBAR );
			fightPts = new SimpleIntegerProperty();
		}
		
		public DoubleProperty hpProperty() {
			return hp;
		}
		
		public DoubleProperty sbarProperty() {
			return sbar;
		}
		
		public IntegerProperty fightPointsProperty() {
			return fightPts;
		}
	}
	
	public static final class MechModel {
		
		private Rectangle head;
		private Rectangle leftArm;
		private Rectangle rightArm;
		private Rectangle body;
		private Rectangle leftLeg;
		private Rectangle rightLeg;
		
		public MechModel() {
			head = new Rectangle();
			leftArm = new Rectangle();
			rightArm = new Rectangle();
			body = new Rectangle();
			leftLeg = new Rectangle();
			rightLeg = new Rectangle();
			
			head.setTranslateX( UIDimensions.HEAD_X );
			head.setTranslateY( UIDimensions.HEAD_Y );
			
			body.setTranslateX( UIDimensions.BODY_X );
			body.setTranslateY( UIDimensions.BODY_ARM_Y );
			
			leftArm.setTranslateX( UIDimensions.LEFT_ARM_X );
			leftArm.setTranslateY( UIDimensions.BODY_ARM_Y );
			
			rightArm.setTranslateX( UIDimensions.RIGHT_ARM_X );
			rightArm.setTranslateY( UIDimensions.BODY_ARM_Y );
			
			leftLeg.setTranslateX( UIDimensions.LEFT_LEG_X );
			leftLeg.setTranslateY( UIDimensions.LEG_Y );
			
			rightLeg.setTranslateX( UIDimensions.RIGHT_LEG_X );
			rightLeg.setTranslateY( UIDimensions.LEG_Y );
			
			head.setWidth( UIDimensions.HEAD_WIDTH );
			head.setHeight( UIDimensions.HEAD_HEIGHT );
			
			body.setWidth( UIDimensions.BODY_WIDTH );
			body.setHeight( UIDimensions.BODY_HEIGHT );
			
			leftArm.setWidth( UIDimensions.ARM_WIDTH );
			leftArm.setHeight( UIDimensions.ARM_HEIGHT );
			
			rightLeg.setWidth( UIDimensions.ARM_WIDTH );
			rightLeg.setHeight( UIDimensions.ARM_HEIGHT );
			
			leftLeg.setWidth( UIDimensions.LEG_WIDTH );
			leftLeg.setHeight( UIDimensions.LEG_HEIGHT );
			
			rightLeg.setWidth( UIDimensions.LEG_WIDTH );
			rightLeg.setHeight( UIDimensions.LEG_HEIGHT );
		}
		
		public Shape getHead() {
			return head;
		}
		
		public Shape getBody() {
			return body;
		}
		
		public Shape getLeftArm() {
			return leftArm;
		}
		
		public Shape getRightArm() {
			return rightArm;
		}
		
		public Shape getLeftLeg() {
			return leftLeg;
		}
		
		public Shape getRightLeg() {
			return rightLeg;
		}
	}
	
	public static final class RegionColorMap {
		
		private ObjectProperty<Color> head;
		private ObjectProperty<Color> arms;
		private ObjectProperty<Color> body;
		private ObjectProperty<Color> legs;
		
		public RegionColorMap( MechModel model ) {
			head = new SimpleObjectProperty<>();
			arms = new SimpleObjectProperty<>();
			body = new SimpleObjectProperty<>();
			legs = new SimpleObjectProperty<>();
			
			model.getHead().fillProperty().bind( head );
			model.getBody().fillProperty().bind( body );
			model.getLeftArm().fillProperty().bind( arms );
			model.getRightArm().fillProperty().bind( arms );
			model.getLeftLeg().fillProperty().bind( legs );
			model.getRightLeg().fillProperty().bind( legs );
		}
		
		public ObjectProperty<Color> headProperty() {
			return head;
		}
		
		public ObjectProperty<Color> armsProperty() {
			return arms;
		}
		
		public ObjectProperty<Color> bodyProperty() {
			return body;
		}
		
		public ObjectProperty<Color> legsProperty() {
			return legs;
		}
	}
	
	public static final class RegionColorBox extends VBox {
		
		private Button colorBtn;
		private Button gradientBtn;
		private ObjectProperty<ColorRegion> currentColorRegion;
		private ObjectProperty<Color> fillProp;
		
		public RegionColorBox( String region, ObjectProperty<Color> fillProp ) {
			super();
			
			Button rgnBtn = new Button( region );
			rgnBtn.setPrefSize( UIDimensions.COLOR_REGION_WIDTH,
					UIDimensions.COLOR_HEADER_HEIGHT );
			rgnBtn.getStyleClass().add( "region-button" );
			
			colorBtn = new Button();
			colorBtn.setPrefSize( UIDimensions.COLOR_SELECT_WIDTH,
					UIDimensions.COLOR_HEADER_HEIGHT );
			colorBtn.setId( "red-color-btn" );
			
			gradientBtn = new Button();
			gradientBtn.setPrefSize( UIDimensions.COLOR_MENU_WIDTH,
					UIDimensions.GRADIENT_HEIGHT );
			gradientBtn.setId( "red-gradient-btn" );
			
			currentColorRegion = new SimpleObjectProperty<>( ColorRegion.RED );
			this.fillProp = fillProp;
			
			colorBtn.setOnMouseClicked( e -> updateRegion() );
			
			currentColorRegion.addListener( e -> {
				String id = currentColorRegion.get().getID();
				colorBtn.setId( id.replace( "--", "-color-" ) );
				gradientBtn.setId( id.replace( "--", "-gradient-" ) );
			});
			
			colorBtn.addEventFilter( MouseEvent.ANY, e -> {
				
				EventType<? extends MouseEvent> event = e.getEventType();
				
				if( event == MouseEvent.MOUSE_CLICKED ||
						event == MouseEvent.MOUSE_DRAGGED ) {
					updateColor( e.getX() );
				}
			});
			
			Pane headerPane = new HBox();
			headerPane.getChildren().setAll( colorBtn, rgnBtn );
			
			getChildren().setAll( headerPane, gradientBtn );
		}
		
		public void updateColor( double refX ) {
			
			double lbx = gradientBtn.getLayoutBounds().getMaxX();
			Color prevColor = fillProp.get();
			
			switch( currentColorRegion.get() ) {
				case RED:
					Color red = new Color( refX / lbx, prevColor.
							getGreen(), prevColor.getBlue(), 1 );
					fillProp.set( red );
					break;
				case GREEN:
					Color green = new Color( prevColor.getRed(),
							refX / lbx, prevColor.getBlue(), 1 );
					fillProp.set( green );
					break;
				case BLUE:
					Color blue = new Color( prevColor.getRed(),
							prevColor.getGreen(), refX / lbx, 1 );
					fillProp.set( blue );
					break;
				default :
					break;
			}
		}
		
		public void updateRegion() {
			ColorRegion curclr = currentColorRegion.get();
			ColorRegion newclr = curclr == ColorRegion.RED ?
				ColorRegion.GREEN : curclr == ColorRegion.GREEN ?
					ColorRegion.BLUE : ColorRegion.RED;
			currentColorRegion.set( newclr );
		}
		
		private static enum ColorRegion {
			
			RED("red--btn"),
			GREEN("green--btn"),
			BLUE("blue--btn");
			
			private String id;
			
			ColorRegion( String id ) {
				this.id = id;
			}
			
			public String getID() {
				return id;
			}
		}
	}
	
	public static final class MechGroup extends Group {
		
		private MechModel model;
		
		public MechGroup() {
			super();
			model = new MechModel();
			getChildren().setAll( model.getHead(),
				model.getBody(), model.getLeftArm(),
				model.getRightArm(), model.getLeftLeg(),
				model.getRightLeg() );
		}
		
		public MechModel getModel() {
			return model;
		}
	}
	
	static interface UIDimensions {
		
		static final double REF_WIDTH = 1920.0 / Screen.
				getPrimary().getBounds().getWidth();
		static final double REF_HEIGHT = 1080.0 /
				Screen.getPrimary().getBounds().getHeight();
		
		static final double TB_PAD = 50.0 * REF_HEIGHT;
		static final double LR_PAD = 150.0 * REF_WIDTH;
		
		static final double TITLE_WIDTH = 1320.0 * REF_WIDTH;
		static final double OPTIONS_WIDTH = 150.0 * REF_WIDTH;
		static final double TITLE_HEIGHT = 100.0 * REF_HEIGHT;
		
		static final double MECH_COMPONENT_HEIGHT = 80 * REF_HEIGHT;
		static final double MECH_GROUP_WIDTH = 520 * REF_WIDTH;
		static final double MECH_GROUP_HEIGHT = 600 * REF_HEIGHT;
		
		static final double TIMER_WIDTH = 500 * REF_WIDTH;
		static final double TIMER_HEIGHT = 240 * REF_HEIGHT;
		static final double TIMER_PAD = 40 * REF_WIDTH;
		
		static final double HPSB_WIDTH = 260 * REF_WIDTH;
		
		static final double COLOR_REGION_WIDTH = 300 * REF_WIDTH;
		static final double COLOR_MENU_WIDTH = 375 * REF_WIDTH;
		static final double COLOR_SELECT_WIDTH = 75 * REF_WIDTH;
		static final double COLOR_HEADER_HEIGHT = 75 * REF_HEIGHT;
		static final double GRADIENT_HEIGHT = 400 * REF_HEIGHT;
		
		static final double HEAD_WIDTH = 150 * REF_WIDTH;
		static final double HEAD_HEIGHT = 150 * REF_WIDTH;
		static final double BODY_WIDTH = 300 * REF_WIDTH;
		static final double BODY_HEIGHT = 200 * REF_HEIGHT;
		static final double ARM_WIDTH = 75 * REF_WIDTH;
		static final double ARM_HEIGHT = 300 * REF_HEIGHT;
		static final double LEG_WIDTH = 100 * REF_WIDTH;
		static final double LEG_HEIGHT = 150 * REF_HEIGHT;
		
		static final double HEAD_X = 185 * REF_WIDTH;
		static final double HEAD_Y = 50 * REF_HEIGHT;
		static final double LEFT_ARM_X = 35 * REF_WIDTH;
		static final double RIGHT_ARM_X = 335 * REF_WIDTH;
		static final double BODY_ARM_Y = 200 * REF_HEIGHT;
		static final double BODY_X = 110 * REF_WIDTH;
		static final double LEFT_LEG_X = 110 * REF_WIDTH;
		static final double RIGHT_LEG_X = 310 * REF_WIDTH;
		static final double LEG_Y = 400 * REF_HEIGHT;
	}
}
