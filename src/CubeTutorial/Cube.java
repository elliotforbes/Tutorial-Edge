package CubeTutorial;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
 
import java.nio.ByteBuffer;
 
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
 
public class Cube {
 
    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback   keyCallback;
 
    // The window handle
    private long window;
 
    public void run() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
 
        try {
            init();
            loop();
 
            // Release window and window callbacks
            glfwDestroyWindow(window);
            keyCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerrorfun
            glfwTerminate();
            errorCallback.release();
        }
    }
 
    private void init() {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
 
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        // Configure our window
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE); // the window will be resizable
 
        int WIDTH = 800;
        int HEIGHT = 600;
 
        // Create the window
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
            }
        });
 
        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);
 
        // Make the window visible
        glfwShowWindow(window);
    }
 
    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();
 
     // Set the clear color
        glClearColor(0.2f, 0.2f, 0.2f, 0.0f);

        float rotAngle = 0.1f;
        
        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
        	glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT | GL_STENCIL_BUFFER_BIT);

    		glMatrixMode(GL_PROJECTION);
	    	glLoadIdentity();         // Reset the model-view matrix
	    	glTranslatef(0.0f, 0.0f, 0.0f); 
	    	
	    	glMatrixMode(GL_MODELVIEW);
        	            
            GL11.glRotatef(rotAngle, 1.0f, 1.0f, 0.0f);
            glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
         // Draw the side faces

            glBegin(GL_QUADS);                // Begin drawing the color cube with 6 quads
               // Top face (y = 1.0f)
               // Define vertices in counter-clockwise (CCW) order with normal pointing out
               glColor3f(0.0f, 1.0f, 0.0f);     // Green
               glVertex3f( 0.2f, 0.2f, -0.2f);
               glVertex3f(-0.2f, 0.2f, -0.2f);
               glVertex3f(-0.2f, 0.2f,  0.2f);
               glVertex3f( 0.2f, 0.2f,  0.2f);
          
               // Bottom face (y = -1.0f)
               glColor3f(1.0f, 0.5f, 0.0f);     // Orange
               glVertex3f( 0.2f, -0.2f,  0.2f);
               glVertex3f(-0.2f, -0.2f,  0.2f);
               glVertex3f(-0.2f, -0.2f, -0.2f);
               glVertex3f( 0.2f, -0.2f, -0.2f);
          
               // Front face  (z = 1.0f)
               glColor3f(1.0f, 0.0f, 0.0f);     // Red
               glVertex3f( 0.2f,  0.2f, 0.2f);
               glVertex3f(-0.2f,  0.2f, 0.2f);
               glVertex3f(-0.2f, -0.2f, 0.2f);
               glVertex3f( 0.2f, -0.2f, 0.2f);
          
               // Back face (z = -1.0f)
               glColor3f(1.0f, 1.0f, 0.0f);     // Yellow
               glVertex3f( 0.2f, -0.2f, -0.2f);
               glVertex3f(-0.2f, -0.2f, -0.2f);
               glVertex3f(-0.2f,  0.2f, -0.2f);
               glVertex3f( 0.2f,  0.2f, -0.2f);
          
               // Left face (x = -1.0f)
               glColor3f(0.0f, 0.0f, 1.0f);     // Blue
               glVertex3f(-0.2f,  0.2f,  0.2f);
               glVertex3f(-0.2f,  0.2f, -0.2f);
               glVertex3f(-0.2f, -0.2f, -0.2f);
               glVertex3f(-0.2f, -0.2f,  0.2f);
          
               // Right face (x = 1.0f)
               glColor3f(1.0f, 0.0f, 1.0f);     // Magenta
               glVertex3f(0.2f,  0.2f, -0.2f);
               glVertex3f(0.2f,  0.2f,  0.2f);
               glVertex3f(0.2f, -0.2f,  0.2f);
               glVertex3f(0.2f, -0.2f, -0.2f);
            glEnd();  // End of drawing color-cube								// Done Dr
            rotAngle = 0.5f;
           
            glfwSwapBuffers(window); // swap the color buffers
            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }
 
    public static void main(String[] args) {
        new Cube().run();
    }
 
}