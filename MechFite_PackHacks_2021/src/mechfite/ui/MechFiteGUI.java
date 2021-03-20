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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
	
	private static Mech playerMech;
	
	private static Mech aiMech;
	
	public static void main( String[] args ) {
		launch( args );
	}
	
	@Override
	public void start( Stage stage ) throws Exception {
		
		Font.loadFont( MechFiteGUI.class.getClass().
				getResourceAsStream( "/fonts/ObliviousFont.ttf" ), 1.0f );
		
		BorderPane mfRoot = new BorderPane();
		
		Scene scene = new Scene( mfRoot );
		scene.getStylesheets().setAll( MechFiteGUI.class.
				getResource( "/css/main.css" ).toExternalForm() );
		
		for( String font : Font.getFamilies() ) {
			System.out.println( font );
		}
		
		rootNodes = new StackPane().getChildren();
		
		Button titleBtn = new Button( "MECHFITE" );
		titleBtn.getStyleClass().add( "title-button" );
		titleBtn.setPrefSize( UIDimensions.TITLE_WIDTH,
				UIDimensions.TITLE_HEIGHT );
		
		mfRoot.setTop( titleBtn );
		
		stage.setScene( scene );
		stage.setMaximized( true );
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
		
		private DoubleProperty hp;
		private DoubleProperty sbar;
		private IntegerProperty fightPts;
		
		public Mech() {
			hp = new SimpleDoubleProperty();
			sbar = new SimpleDoubleProperty();
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
		
		private Shape head;
		private Shape leftArm;
		private Shape rightArm;
		private Shape body;
		private Shape leftLeg;
		private Shape rightLeg;
		
		public MechModel() {
			head = new Rectangle();
			leftArm = new Rectangle();
			rightArm = new Rectangle();
			body = new Rectangle();
			leftLeg = new Rectangle();
			rightLeg = new Rectangle();
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
			rgnBtn.setPrefSize( UIDimensions.COLOR_MENU_WIDTH,
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
				colorBtn.setId( id );
				gradientBtn.setId( id.replace( "color", "gradient" ) );
			});
			
			colorBtn.addEventFilter( MouseEvent.ANY, e -> {
				
				EventType<? extends MouseEvent> event = e.getEventType();
				
				if( event == MouseEvent.MOUSE_CLICKED ||
						event == MouseEvent.MOUSE_DRAGGED ) {
					updateColor( e.getX() );
				}
			});
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
			
			RED("red-color-btn"),
			GREEN("green-color-btn"),
			BLUE("blue-color-btn");
			
			private String id;
			
			ColorRegion( String id ) {
				this.id = id;
			}
			
			public String getID() {
				return id;
			}
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
		
		static final double COLOR_MENU_WIDTH = 300 * REF_WIDTH;
		static final double COLOR_SELECT_WIDTH = 50 * REF_WIDTH;
		static final double COLOR_HEADER_HEIGHT = 75 * REF_HEIGHT;
		static final double GRADIENT_HEIGHT = 400 * REF_HEIGHT;
	}
}
