package bad.robot.radiate.ui

import java.awt.Font.BOLD
import java.awt.RenderingHints.{KEY_ANTIALIASING, VALUE_ANTIALIAS_ON}
import java.awt._
import java.awt.event.WindowEvent._
import java.awt.event._
import java.awt.geom.{Area, RoundRectangle2D}
import java.beans.{PropertyChangeEvent, PropertyChangeListener}
import java.lang.ref.WeakReference
import java.lang.reflect.Method
import javax.swing.border.Border
import javax.swing.event.{AncestorEvent, AncestorListener}
import javax.swing.{FocusManager, _}

import bad.robot.radiate.ui.TransparentDialogS._

object TransparentDialogS {
  val ROUNDED_RECT_DIAMETER: Int = 16

  private object TitlePanel {
    val FONT_COLOR = new Color(255, 255, 255, 255)
    val UNFOCUSED_FONT_COLOR = new Color(0xcccccc)
    val HIGHLIGHT = new Color(255, 255, 255, 25)
    val TOP_BACKGROUND_TOP = new Color(255, 255, 255, 59)
    val TOP_BACKGROUND_BOTTOM = new Color(196, 196, 196, 59)
    val BOTTOM_BACKGROUND = new Color(255, 255, 255, 30)
    val UNFOCUSED_BACKGROUND = new Color(0, 0, 0, 10)
    val CLOSE_ICON = new ImageIcon(classOf[TitlePanel].getResource("/bad/robot/radiate/ui/close.png"))
    val CLOSE_HOVER_ICON = new ImageIcon(classOf[TitlePanel].getResource("/bad/robot/radiate/ui/close_hover.png"))
    val CLOSE_PRESSED_ICON = new ImageIcon(classOf[TitlePanel].getResource("/bad/robot/radiate/ui/close_pressed.png"))

    private[ui] def isMac: Boolean = {
      System.getProperty("os.name").toLowerCase.startsWith("mac")
    }

    def createSpacer(width: Int, height: Int): JComponent = {
      val label = new JLabel
      label.setOpaque(false)
      label.setPreferredSize(new Dimension(width, height))
      label
    }
  }

  private class TitlePanel(title: String, closeButtonActionListener: ActionListener) extends JPanel {
    val close = new JButton(TitlePanel.CLOSE_ICON)
    val spacer = TitlePanel.createSpacer(close.getPreferredSize.width, 0)
    val label = new JLabel(title, SwingConstants.CENTER)

    label.setFont(label.getFont.deriveFont(BOLD, 11.0f))
    setOpaque(false)
    setPreferredSize(new Dimension(-1, 20))
    updateFocusState
    close.setBorder(getCloseButtonBorder)
    close.setVerticalAlignment(SwingConstants.CENTER)
    close.setOpaque(false)
    close.setFocusable(false)
    close.setBorderPainted(false)
    close.setContentAreaFilled(false)
    close.setRolloverIcon(TitlePanel.CLOSE_HOVER_ICON)
    close.setPressedIcon(TitlePanel.CLOSE_PRESSED_ICON)
    close.addActionListener(closeButtonActionListener)
    setLayout(new BorderLayout)
    add(label, BorderLayout.CENTER)
    add(close, if (TitlePanel.isMac) BorderLayout.WEST else BorderLayout.EAST)
    add(spacer, if (TitlePanel.isMac) BorderLayout.EAST else BorderLayout.WEST)

    def hideCloseButton() {
      close.setVisible(false)
      spacer.setVisible(false)
    }

    private def getCloseButtonBorder: Border = {
      if (TitlePanel.isMac) BorderFactory.createEmptyBorder(0, 5, 0, 0) else BorderFactory.createEmptyBorder(0, 0, 0, 5)
    }

    private[ui] def setTitle(title: String) {
      label.setText(title)
    }

    private def updateFocusState() {
      val focused = WindowUtils.isParentWindowFocused(this)
      label.setForeground(if (focused) TitlePanel.FONT_COLOR else TitlePanel.UNFOCUSED_FONT_COLOR)
    }

    protected override def paintComponent(g: Graphics) {
      val graphics = g.create.asInstanceOf[Graphics2D]
      graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
      val midPointY = ROUNDED_RECT_DIAMETER / 2 + 3
      if (WindowUtils.isParentWindowFocused(this)) {
        val paint = new GradientPaint(0, 0, TitlePanel.TOP_BACKGROUND_TOP, 0, midPointY, TitlePanel.TOP_BACKGROUND_BOTTOM)
        graphics.setPaint(paint)
        val titleArea = new Area(new Area(new RoundRectangle2D.Double(0, 0, getWidth, getHeight, ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER)))
        titleArea.subtract(new Area(new Rectangle(0, midPointY, getWidth, midPointY)))
        graphics.fill(titleArea)
        val bottomHeight = getHeight - midPointY
        graphics.setColor(TitlePanel.BOTTOM_BACKGROUND)
        graphics.fillRect(0, midPointY, getWidth, bottomHeight)
      } else {
        graphics.setColor(TitlePanel.UNFOCUSED_BACKGROUND)
        val titleArea: Area = new Area(new Area(new RoundRectangle2D.Double(0, 0, getWidth, getHeight, ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER)))
        titleArea.subtract(new Area(new Rectangle(0, midPointY, getWidth, midPointY)))
        graphics.fill(titleArea)
        graphics.setColor(TitlePanel.HIGHLIGHT)
        graphics.drawLine(0, getHeight - 1, getWidth, getHeight - 1)
      }
      graphics.dispose()
    }
  }

  private object HudPanel {
    private val HIGHLIGHT: Color = new Color(255, 255, 255, 59)
    private val HIGHLIGHT_BOTTOM: Color = new Color(255, 255, 255, 25)
    private val BACKGROUND: Color = new Color(30, 30, 30, 216)
  }

  private class HudPanel extends JPanel {
    setLayout(new BorderLayout)
    setOpaque(false)

    override def paintBorder(g: Graphics) {
      val graphics = g.create.asInstanceOf[Graphics2D]
      graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
      val paint = new GradientPaint(0, 0, HudPanel.HIGHLIGHT, 0, getHeight, HudPanel.HIGHLIGHT_BOTTOM)
      graphics.setPaint(paint)
      graphics.drawRoundRect(0, 0, getWidth - 1, getHeight - 1, ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER)
      graphics.dispose()
    }

    override def paintComponent(g: Graphics) {
      val graphics2d = g.create.asInstanceOf[Graphics2D]
      graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON)
      graphics2d.setComposite(AlphaComposite.Src)
      graphics2d.setColor(HudPanel.BACKGROUND)
      graphics2d.fillRoundRect(0, 0, getWidth, getHeight, ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER)
      getRootPane.putClientProperty("apple.awt.windowShadow.revalidateNow", new AnyRef)
      graphics2d.dispose()
    }
  }

  private object BottomPanel {
    private val RESIZE_ICON: Icon = new ImageIcon(classOf[TitlePanel].getResource("/bad/robot/radiate/ui/resize_corner_dark.png"))
  }

  private class BottomPanel(window: Window) extends JPanel(new FlowLayout(SwingConstants.RIGHT)) {
    private val resizeCorner = new JLabel(BottomPanel.RESIZE_ICON)
    private var xOffsetToWindowEdge = 0
    private var yOffsetToWindowEdge = 0

    setOpaque(false)
    add(resizeCorner)
    resizeCorner.addMouseListener(createMouseListener)
    resizeCorner.addMouseMotionListener(createMouseMotionListener)

    private def createMouseListener: MouseAdapter = {
      new MouseAdapter() {
        override def mousePressed(e: MouseEvent) {
          val windowPoint: Point = SwingUtilities.convertPoint(resizeCorner, e.getPoint, window)
          xOffsetToWindowEdge = window.getWidth - windowPoint.x
          yOffsetToWindowEdge = window.getHeight - windowPoint.y
        }
      }
    }

    private def createMouseMotionListener: MouseMotionListener = {
      new MouseMotionAdapter() {
        override def mouseDragged(e: MouseEvent) {
          val windowPoint: Point = SwingUtilities.convertPoint(resizeCorner, e.getPoint, window)
          window.setSize(windowPoint.x + xOffsetToWindowEdge, windowPoint.y + yOffsetToWindowEdge)
          window.invalidate()
          window.validate()
        }
      }
    }
  }

  object WindowUtils {
    /**
     * Tries to make the given `Window` non-opaque (transparent) across platforms and JREs. This method is not
     * guaranteed to succeed, and will fail silently if the given `Window` cannot be made non-opaque.
     *
     * This method is useful, for example, when creating a HUD style window that is semi-transparent, and thus doesn't
     * want the window background to be drawn.
     *
     * @param window the `Window` to make non-opaque.
     */
    def makeWindowNonOpaque(window: Window) {
      window.setBackground(new Color(0, 0, 0, 0))
      if (!TitlePanel.isMac)
        quietlyTryToMakeWindowNonOqaque(window)
    }

    /**
     * Tries to invoke {@code com.sun.awt.AWTUtilities.setWindowOpaque(window, false)} on the given {@link Window}.
     * This will only work when running with JRE 6 update 10 or higher. This method will silently fail if the method
     * cannot be invoked.
     *
     * @param window the { @code Window} to try and make non-opaque.
     */
    private def quietlyTryToMakeWindowNonOqaque(window: Window) {
      try {
        val clazz: Class[_] = Class.forName("com.sun.awt.AWTUtilities")
        val method: Method = clazz.getMethod("setWindowOpaque", classOf[Window], classOf[Boolean])
        val bool: Boolean = false
        method.invoke(clazz, window)
      } catch {
        case _: Exception => { /* silently ignore */ }
      }
    }

    /**
     * Creates and installs a {@link WindowFocusListener} on the given {@link Window} which calls the {@code Window}'s
     * {@code repaint()} method on focus state changes.
     *
     * @param window the { @code Window} to repaint on focus state changes.
     * @return the listener installed.
     * @deprecated use the more targeted
     *             { @link WindowUtils#installJComponentRepainterOnWindowFocusChanged(JComponent)}
     *                     method.
     */
    // @deprecated("use the more targeted [[installJComponentRepainterOnWindowFocusChanged(JComponent)]] method", "")
    def createAndInstallRepaintWindowFocusListener(window: Window): WindowFocusListener = {
      val windowFocusListener: WindowFocusListener = new WindowFocusListener() {
        def windowGainedFocus(event: WindowEvent) {
          event.getWindow.repaint()
        }

        def windowLostFocus(event: WindowEvent) {
          event.getWindow.repaint()
        }
      }
      window.addWindowFocusListener(windowFocusListener)
      windowFocusListener
    }

    /**
     * {@code true} if the given {@link Component}'s has a parent {@link Window}
     * (i.e. it's not null) and that {@link Window} is currently active
     * (focused).
     *
     * @param component the { @code Component} to check the parent { @code Window}'s focus for
     * @return { @code true} if the given { @code Component}'s parent { @code Window} is currently active.
     */
    def isParentWindowFocused(component: Component): Boolean = {
      val window = SwingUtilities.getWindowAncestor(component)
      window != null && window.isFocused
    }

    /**
     * Installs a {@link WindowFocusListener} on the given {@link JComponent}'s
     * parent {@link Window}. If the {@code JComponent} doesn't yet have a
     * parent, then the listener will be installed when the component is added
     * to a container.
     *
     * @param component     the component who's parent frame to listen to focus changes on.
     * @param focusListener the { @code WindowFocusListener} to notify when focus changes.
     */
    def installWeakWindowFocusListener(component: JComponent, focusListener: WindowFocusListener) {
      val ancestorListener = createAncestorListener(component, focusListener)
      component.addAncestorListener(ancestorListener)
    }

    /**
     * Installs a listener on the given {@link JComponent}'s parent {@link Window} that repaints the given component
     * when the parent window's focused state changes. If the given component does not have a parent at the time this
     * method is called, then an ancestor listener will be installed that installs a window listener when the components
     * parent changes.
     *
     * @param component the { @code JComponent} to add the repaint focus listener to.
     */
    def installJComponentRepainterOnWindowFocusChanged(component: JComponent) {
      val windowListener = createRepaintWindowListener(component)
      val ancestorListener = createAncestorListener(component, windowListener)
      component.addAncestorListener(ancestorListener)
    }

    private def createAncestorListener(component: JComponent, windowListener: WindowFocusListener): AncestorListener = {
      val weakReference: WeakReference[JComponent] = new WeakReference[JComponent](component)
      new AncestorListener() {
        def ancestorAdded(event: AncestorEvent) {
          val window = if (weakReference.get == null) null else SwingUtilities.getWindowAncestor(weakReference.get)
          if (window != null) {
            window.removeWindowFocusListener(windowListener)
            window.addWindowFocusListener(windowListener)
            fireInitialFocusEvent(windowListener, window)
          }
        }

        private def fireInitialFocusEvent(windowListener: WindowFocusListener, window: Window) {
          val focusedWindow: Window = FocusManager.getCurrentManager.getFocusedWindow
          if (window eq focusedWindow)
            windowListener.windowGainedFocus(new WindowEvent(window, WINDOW_GAINED_FOCUS))
          else
            windowListener.windowGainedFocus(new WindowEvent(window, WINDOW_LOST_FOCUS))
        }

        def ancestorRemoved(event: AncestorEvent) {
          val window: Window = if (weakReference.get == null) null else SwingUtilities.getWindowAncestor(weakReference.get)
          if (window != null)
            window.removeWindowFocusListener(windowListener)
        }

        def ancestorMoved(event: AncestorEvent) { }
      }
    }

    private def createRepaintWindowListener(component: JComponent): WindowFocusListener = {
      new WindowFocusListener() {
        def windowGainedFocus(e: WindowEvent) {
          component.repaint()
        }

        def windowLostFocus(e: WindowEvent) {
          component.repaint()
        }
      }
    }
  }

}

class TransparentDialogS(title: String, owner: Frame) {
  private val dialog: JDialog = new JDialog(owner)
  private var contentPane: JComponent = null
  private val titlePanel = new TitlePanel(title, createCloseButtonActionListener)
  private val panel: HudPanel = new HudPanel
  private val bottomPanel = new BottomPanel(dialog)

  dialog.setTitle(title)
  init()

  def this(title: String) {
    this(title, null)
  }

  def this() {
    this("")
  }


  private def init() {
    dialog.getRootPane.putClientProperty("apple.awt.draggableWindowBackground", classOf[Boolean])
    dialog.setUndecorated(true)
    dialog.getRootPane.setOpaque(false)
    WindowUtils.makeWindowNonOpaque(dialog)
    dialog.getRootPane.setBackground(Color.BLACK)
    this.panel.add(titlePanel, BorderLayout.NORTH)
    dialog.setContentPane(this.panel)

    val panel = new JPanel
    panel.setOpaque(false)
    setContentPane(panel)
    dialog.addPropertyChangeListener("title", createTitlePropertyChangeListener)
    WindowUtils.createAndInstallRepaintWindowFocusListener(dialog)
    new WindowDragger(dialog, titlePanel)
  }

  def getJDialog: JDialog = {
    dialog
  }

  def hideCloseButton() {
    titlePanel.hideCloseButton()
  }

  def makeResizeable() {
    panel.add(bottomPanel, BorderLayout.SOUTH)
  }

  def getContentPane: JComponent = contentPane

  def setContentPane(contentPane: JComponent) {
    if (this.contentPane != null)
      panel.remove(this.contentPane)
    this.contentPane = contentPane
    panel.add(this.contentPane, BorderLayout.CENTER)
  }

  private def createTitlePropertyChangeListener: PropertyChangeListener = new PropertyChangeListener {
    def propertyChange(event: PropertyChangeEvent): Unit = titlePanel.setTitle(dialog.getTitle)
  }

  private def createCloseButtonActionListener: ActionListener = new ActionListener {
    def actionPerformed(event: ActionEvent): Unit = dialog.dispatchEvent(new WindowEvent(dialog, WINDOW_CLOSING))
  }

  def setVisible(visible: Boolean) {
    dialog.setVisible(visible)
  }

  def isVisible: Boolean = dialog.isVisible

  class WindowDragger(window: Window, component: Component) {
    private var x = 0
    private var y = 0

    component.addMouseListener(createMouseListener)
    component.addMouseMotionListener(createMouseMotionListener)

    private def createMouseListener: MouseListener = {
      new MouseAdapter() {
        override def mousePressed(e: MouseEvent) {
          val clickPoint: Point = new Point(e.getPoint)
          SwingUtilities.convertPointToScreen(clickPoint, component)
          x = clickPoint.x - window.getX
          y = clickPoint.y - window.getY
        }
      }
    }

    private def createMouseMotionListener: MouseMotionAdapter = {
      new MouseMotionAdapter() {
        override def mouseDragged(e: MouseEvent) {
          val dragPoint = new Point(e.getPoint)
          SwingUtilities.convertPointToScreen(dragPoint, component)
          window.setLocation(dragPoint.x - x, dragPoint.y - y)
        }
      }
    }
  }

}