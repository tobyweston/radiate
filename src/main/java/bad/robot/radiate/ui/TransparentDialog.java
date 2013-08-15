package bad.robot.radiate.ui;

import javax.swing.*;
import javax.swing.FocusManager;
import javax.swing.border.Border;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.geom.RoundRectangle2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;

import static java.awt.Font.BOLD;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.event.WindowEvent.*;


public class TransparentDialog {

    private final JDialog dialog;
    private JComponent contentPane;
    private final TitlePanel titlePanel;
    private final HudPanel panel = new HudPanel();
    private final BottomPanel bottomPanel;

    private static final int ROUNDED_RECT_DIAMETER = 16;

    public TransparentDialog() {
        this("");
    }

    public TransparentDialog(String title) {
        this(title, null);
    }

    public TransparentDialog(String title, Frame owner) {
        dialog = new JDialog(owner);
        dialog.setTitle(title);
        titlePanel = new TitlePanel(title, createCloseButtonActionListener());
        bottomPanel = new BottomPanel(dialog);
        init();
    }

    private void init() {
        // indicate that this frame should not make all the content draggable. by default, when you
        // set the opacity to 0 (like we do below) this property automatically gets set to true.
        // also note that this client property must be set *before* changing the opacity (not sure why).
        dialog.getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
        dialog.setUndecorated(true);
        dialog.getRootPane().setOpaque(false);

        WindowUtils.makeWindowNonOpaque(dialog);
        // for Java 5 on platforms other than Mac (those that don't support transparency), it looks
        // nicer to use a black background rather than the default (usually white).
        dialog.getRootPane().setBackground(Color.BLACK);

        panel.add(titlePanel, BorderLayout.NORTH);

        // set the backing frame's content pane.
        dialog.setContentPane(panel);
        // set the HUD panel's content pane to a blank JPanel by default.
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        setContentPane(panel);

        // listen to the frame's title property so that we can update the label rendering the title.
        dialog.addPropertyChangeListener("title", createTitlePropertyChangeListener());

        WindowUtils.createAndInstallRepaintWindowFocusListener(dialog);
        new WindowDragger(dialog, titlePanel);
    }

    public JDialog getJDialog() {
        return dialog;
    }

    public void hideCloseButton() {
        titlePanel.hideCloseButton();
    }

    public void makeResizeable() {
        panel.add(bottomPanel, BorderLayout.SOUTH);
    }

    public JComponent getContentPane() {
        return contentPane;
    }

    public void setContentPane(JComponent contentPane) {
        if (this.contentPane != null)
            panel.remove(this.contentPane);
        this.contentPane = contentPane;
        panel.add(this.contentPane, BorderLayout.CENTER);
    }

    private PropertyChangeListener createTitlePropertyChangeListener() {
        return new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent event) {
                titlePanel.setTitle(dialog.getTitle());
            }
        };
    }

    private ActionListener createCloseButtonActionListener() {
        return new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                // simulate clicking the "real" close button on a window.
                dialog.dispatchEvent(new WindowEvent(dialog, WINDOW_CLOSING));
            }
        };
    }

    public void setVisible(boolean visible) {
        dialog.setVisible(visible);
    }

    public boolean isVisible() {
        return dialog.isVisible();
    }

    private static class TitlePanel extends JPanel {

        private static final Color FONT_COLOR = new Color(255, 255, 255, 255);
        private static final Color UNFOCUSED_FONT_COLOR = new Color(0xcccccc);
        private static final Color HIGHLIGHT = new Color(255, 255, 255, 25);
        private static final Color TOP_BACKGROUND_TOP = new Color(255, 255, 255, 59);
        private static final Color TOP_BACKGROUND_BOTTOM = new Color(196, 196, 196, 59);
        private static final Color BOTTOM_BACKGROUND = new Color(255, 255, 255, 30);
        private static final Color UNFOCUSED_BACKGROUND = new Color(0, 0, 0, 10);

        private static final Icon CLOSE_ICON = new ImageIcon(TitlePanel.class.getResource("/bad/robot/radiate/ui/close.png"));
        private static final Icon CLOSE_HOVER_ICON = new ImageIcon(TitlePanel.class.getResource("/bad/robot/radiate/ui/close_hover.png"));
        private static final Icon CLOSE_PRESSED_ICON = new ImageIcon(TitlePanel.class.getResource("/bad/robot/radiate/ui/close_pressed.png"));

        private final JButton close = new JButton(CLOSE_ICON);
        private final JComponent spacer;
        private final JLabel label;

        private TitlePanel(String title, ActionListener closeButtonActionListener) {
            label = new JLabel(title, SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(BOLD, 11.0f));

            setOpaque(false);
            setPreferredSize(new Dimension(-1, 20));
            updateFocusState();

            close.setBorder(getCloseButtonBorder());
            close.setVerticalAlignment(SwingConstants.CENTER);
            close.setOpaque(false);
            close.setFocusable(false);
            close.setBorderPainted(false);
            close.setContentAreaFilled(false);
            close.setRolloverIcon(CLOSE_HOVER_ICON);
            close.setPressedIcon(CLOSE_PRESSED_ICON);
            close.addActionListener(closeButtonActionListener);

            spacer = createSpacer(close.getPreferredSize().width, 0);

            setLayout(new BorderLayout());
            add(label, BorderLayout.CENTER);
            add(close, isMac() ? BorderLayout.WEST : BorderLayout.EAST);
            add(spacer, isMac() ? BorderLayout.EAST : BorderLayout.WEST);
        }

        static boolean isMac() {
            return System.getProperty("os.name").toLowerCase().startsWith("mac");
        }

        public static JComponent createSpacer(int width, int height) {
            JLabel label = new JLabel();
            label.setOpaque(false);
            label.setPreferredSize(new Dimension(width, height));
            return label;
        }

        private void hideCloseButton() {
            close.setVisible(false);
            spacer.setVisible(false);
        }

        private Border getCloseButtonBorder() {
            return isMac() ? BorderFactory.createEmptyBorder(0, 5, 0, 0) : BorderFactory.createEmptyBorder(0, 0, 0, 5);
        }

        private void setTitle(String title) {
            label.setText(title);
        }

        private void updateFocusState() {
            Boolean focused = WindowUtils.isParentWindowFocused(this);
            label.setForeground(focused == null || focused ? FONT_COLOR : UNFOCUSED_FONT_COLOR);
        }

        @Override
        protected void paintComponent(Graphics g) {
            // create a copy of the graphics object and turn on anti-aliasing.
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

            // calculate the point in the title bar at which to change the background color.
            int midPointY = ROUNDED_RECT_DIAMETER / 2 + 3;

            // if the window has focus, draw a shiny title bar else draw a flat background.
            if (WindowUtils.isParentWindowFocused(this)) {
                // 1. The top half --------------------------------------------------------------//
                // create and set the shiny paint.
                GradientPaint paint = new GradientPaint(0, 0, TOP_BACKGROUND_TOP, 0, midPointY, TOP_BACKGROUND_BOTTOM);
                graphics.setPaint(paint);
                // create a rounded rectangle area as big as the entire title bar, then subtract
                // off the bottom half (roughly) in order to have perfectly square edges.
                Area titleArea = new Area(new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER)));
                titleArea.subtract(new Area(new Rectangle(0, midPointY, getWidth(), midPointY)));
                // draw the top half of the title bar (the shine).
                graphics.fill(titleArea);
                // 2. The bottom half -----------------------------------------------------------//
                // draw the bottom half of the title bar.
                int bottomHeight = getHeight() - midPointY;
                graphics.setColor(BOTTOM_BACKGROUND);
                graphics.fillRect(0, midPointY, getWidth(), bottomHeight);
            } else {
                // create an area that has rounded corners at the top and square corners at the bottom.
                graphics.setColor(UNFOCUSED_BACKGROUND);
                Area titleArea = new Area(new Area(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER)));
                titleArea.subtract(new Area(new Rectangle(0, midPointY, getWidth(), midPointY)));
                graphics.fill(titleArea);
                graphics.setColor(HIGHLIGHT);
                graphics.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
            }

            graphics.dispose();
        }

    }

    private static class HudPanel extends JPanel {

        private static final Color HIGHLIGHT = new Color(255, 255, 255, 59);
        private static final Color HIGHLIGHT_BOTTOM = new Color(255, 255, 255, 25);
        private static final Color BACKGROUND = new Color(30, 30, 30, 216);

        private HudPanel() {
            setLayout(new BorderLayout());
            setOpaque(false);
        }

        @Override
        protected void paintBorder(Graphics g) {
            // create a copy of the graphics object and turn on anti-aliasing.
            Graphics2D graphics = (Graphics2D) g.create();
            graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
            // paint a border around the window that fades slightly to give a more pleasant highlight to the window edges.
            GradientPaint paint = new GradientPaint(0, 0, HIGHLIGHT, 0, getHeight(), HIGHLIGHT_BOTTOM);
            graphics.setPaint(paint);
            graphics.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER);
            graphics.dispose();
        }

        @Override
        protected void paintComponent(Graphics g) {
            // create a copy of the graphics object and turn on anti-aliasing.
            Graphics2D graphics2d = (Graphics2D) g.create();
            graphics2d.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

            graphics2d.setComposite(AlphaComposite.Src);

            // draw the rounded rectangle background of the window.
            graphics2d.setColor(BACKGROUND);
            graphics2d.fillRoundRect(0, 0, getWidth(), getHeight(), ROUNDED_RECT_DIAMETER, ROUNDED_RECT_DIAMETER);
            // tell the shadow to revalidate.
            getRootPane().putClientProperty("apple.awt.windowShadow.revalidateNow", new Object());

            graphics2d.dispose();
        }

    }

    private static class BottomPanel extends JPanel {

        private static final Icon RESIZE_ICON = new ImageIcon(TitlePanel.class.getResource("/bad/robot/radiate/ui/resize_corner_dark.png"));

        private final Window window;
        private final JLabel resizeCorner = new JLabel(RESIZE_ICON);
        private int xOffsetToWindowEdge;
        private int yOffsetToWindowEdge;

        public BottomPanel(Window window) {
            super(new FlowLayout(SwingConstants.RIGHT));
            this.window = window;
            setOpaque(false);
            add(resizeCorner);
            resizeCorner.addMouseListener(createMouseListener());
            resizeCorner.addMouseMotionListener(createMouseMotionListener());
        }

        private MouseAdapter createMouseListener() {
            return new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point windowPoint = SwingUtilities.convertPoint(resizeCorner, e.getPoint(), window);
                    xOffsetToWindowEdge = window.getWidth() - windowPoint.x;
                    yOffsetToWindowEdge = window.getHeight() - windowPoint.y;
                }
            };
        }

        private MouseMotionListener createMouseMotionListener() {
            return new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point windowPoint = SwingUtilities.convertPoint(resizeCorner, e.getPoint(), window);
                    window.setSize(windowPoint.x + xOffsetToWindowEdge, windowPoint.y + yOffsetToWindowEdge);

                    // the following two lines are a work-around to Sun bug 6318144: http://bugs.sun.com/view_bug.do;?bug_id=6318144
                    window.invalidate();
                    window.validate();
                }
            };
        }

    }

    public class WindowDragger {

        private Window window;
        private Component component;
        private int x;
        private int y;

        public WindowDragger(Window window, Component component) {
            this.window = window;
            this.component = component;

            this.component.addMouseListener(createMouseListener());
            this.component.addMouseMotionListener(createMouseMotionListener());
        }

        private MouseListener createMouseListener() {
            return new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point clickPoint = new Point(e.getPoint());
                    SwingUtilities.convertPointToScreen(clickPoint, component);

                    x = clickPoint.x - window.getX();
                    y = clickPoint.y - window.getY();
                }
            };
        }

        private MouseMotionAdapter createMouseMotionListener() {
            return new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    Point dragPoint = new Point(e.getPoint());
                    SwingUtilities.convertPointToScreen(dragPoint, component);
                    window.setLocation(dragPoint.x - x, dragPoint.y - y);
                }
            };
        }

    }

    public static class WindowUtils {

        /**
         * Tries to make the given {@link Window} non-opaque (transparent) across platforms and JREs. This method is not
         * guaranteed to succeed, and will fail silently if the given {@code Window} cannot be made non-opaque.
         * <p/>
         * This method is useful, for example, when creating a HUD style window that is semi-transparent, and thus doesn't
         * want the window background to be drawn.
         *
         * @param window the {@code Window} to make non-opaque.
         */
        public static void makeWindowNonOpaque(Window window) {
            // on the mac, simply setting the window's background color to be fully transparent makes the window non-opaque.
            window.setBackground(new Color(0, 0, 0, 0));
            // on non-mac platforms, try to use the facilities of Java 6 update 10.
            if (!TitlePanel.isMac())
                quietlyTryToMakeWindowNonOqaque(window);
        }

        /**
         * Tries to invoke {@code com.sun.awt.AWTUtilities.setWindowOpaque(window, false)} on the given {@link Window}.
         * This will only work when running with JRE 6 update 10 or higher. This method will silently fail if the method
         * cannot be invoked.
         *
         * @param window the {@code Window} to try and make non-opaque.
         */
        private static void quietlyTryToMakeWindowNonOqaque(Window window) {
            try {
                Class type = Class.forName("com.sun.awt.AWTUtilities");
                Method method = type.getMethod("setWindowOpaque", java.awt.Window.class, Boolean.TYPE);
                method.invoke(type, window, false);
            } catch (Exception e) {
                // silently ignore this exception.
            }
        }

        /**
         * Creates and installs a {@link WindowFocusListener} on the given {@link Window} which calls the {@code Window}'s
         * {@code repaint()} method on focus state changes.
         *
         * @param window the {@code Window} to repaint on focus state changes.
         * @return the listener installed.
         * @deprecated use the more targeted
         *             {@link WindowUtils#installJComponentRepainterOnWindowFocusChanged(JComponent)}
         *             method.
         */
        @Deprecated
        public static WindowFocusListener createAndInstallRepaintWindowFocusListener(Window window) {
            WindowFocusListener windowFocusListener = new WindowFocusListener() {
                public void windowGainedFocus(WindowEvent event) {
                    event.getWindow().repaint();
                }

                public void windowLostFocus(WindowEvent event) {
                    event.getWindow().repaint();
                }
            };
            window.addWindowFocusListener(windowFocusListener);
            return windowFocusListener;
        }

        /**
         * {@code true} if the given {@link Component}'s has a parent {@link Window}
         * (i.e. it's not null) and that {@link Window} is currently active
         * (focused).
         *
         * @param component the {@code Component} to check the parent {@code Window}'s focus for
         * @return {@code true} if the given {@code Component}'s parent {@code Window} is currently active.
         */
        public static boolean isParentWindowFocused(Component component) {
            Window window = SwingUtilities.getWindowAncestor(component);
            return window != null && window.isFocused();
        }

        /**
         * Installs a {@link WindowFocusListener} on the given {@link JComponent}'s
         * parent {@link Window}. If the {@code JComponent} doesn't yet have a
         * parent, then the listener will be installed when the component is added
         * to a container.
         *
         * @param component     the component who's parent frame to listen to focus changes on.
         * @param focusListener the {@code WindowFocusListener} to notify when focus changes.
         */
        public static void installWeakWindowFocusListener(JComponent component, WindowFocusListener focusListener) {
            AncestorListener ancestorListener = createAncestorListener(component, focusListener);
            component.addAncestorListener(ancestorListener);
        }

        /**
         * Installs a listener on the given {@link JComponent}'s parent {@link Window} that repaints the given component
         * when the parent window's focused state changes. If the given component does not have a parent at the time this
         * method is called, then an ancestor listener will be installed that installs a window listener when the components
         * parent changes.
         *
         * @param component the {@code JComponent} to add the repaint focus listener to.
         */
        public static void installJComponentRepainterOnWindowFocusChanged(JComponent component) {
            WindowFocusListener windowListener = createRepaintWindowListener(component);
            AncestorListener ancestorListener = createAncestorListener(component, windowListener);
            component.addAncestorListener(ancestorListener);
        }

        private static AncestorListener createAncestorListener(JComponent component, final WindowFocusListener windowListener) {
            final WeakReference<JComponent> weakReference = new WeakReference<>(component);
            return new AncestorListener() {
                public void ancestorAdded(AncestorEvent event) {
                    final Window window = weakReference.get() == null ? null : SwingUtilities.getWindowAncestor(weakReference.get());
                    if (window != null) {
                        window.removeWindowFocusListener(windowListener);
                        window.addWindowFocusListener(windowListener);
                        // notify the listener of the original focus state of the window, which ensures that the listener is in sync with the actual window state.
                        fireInitialFocusEvent(windowListener, window);
                    }
                }

                private void fireInitialFocusEvent(WindowFocusListener windowListener, Window window) {
                    Window focusedWindow = FocusManager.getCurrentManager().getFocusedWindow();
                    // fire a fake event to the given listener indicating the actual focus state of the given window.
                    if (window == focusedWindow)
                        windowListener.windowGainedFocus(new WindowEvent(window, WINDOW_GAINED_FOCUS));
                    else
                        windowListener.windowGainedFocus(new WindowEvent(window, WINDOW_LOST_FOCUS));
                }

                public void ancestorRemoved(AncestorEvent event) {
                    Window window = weakReference.get() == null ? null : SwingUtilities.getWindowAncestor(weakReference.get());
                    if (window != null)
                        window.removeWindowFocusListener(windowListener);
                }

                public void ancestorMoved(AncestorEvent event) {
                }
            };
        }

        private static WindowFocusListener createRepaintWindowListener(final JComponent component) {
            return new WindowFocusListener() {
                public void windowGainedFocus(WindowEvent e) {
                    component.repaint();
                }

                public void windowLostFocus(WindowEvent e) {
                    component.repaint();
                }
            };
        }

    }

}