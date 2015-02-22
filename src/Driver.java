import org.lwjgl.Sys;
import org.lwjgl.opengl.*;
import org.lwjgl.system.glfw.*;
 
import java.nio.ByteBuffer;
 
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.glfw.GLFW.*;
 
public class Driver {
 
    private static long window;
 
    public void execute() {
        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");
 
        try {
            init();
            loop();
            glfwDestroyWindow(window);
        } finally {
            glfwTerminate();
        }
    }
 
    private void init() {
        glfwSetErrorCallback(ErrorCallback.Util.getDefault());
 
        if ( glfwInit() != GL11.GL_TRUE )
            throw new IllegalStateException("Unable to initialize GLFW");
 
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
 
        int WIDTH = 800;
        int HEIGHT = 600;
 
        window = glfwCreateWindow(WIDTH, HEIGHT, "Hello World!", NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");
 
        WindowCallback.set(window, new WindowCallbackAdapter() {
            @Override
            public void key(long window, int key, int scancode, int action, int mods) {
                if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                    glfwSetWindowShouldClose(window, GL_TRUE);
            }
        });
 
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(
            window,
            (GLFWvidmode.width(vidmode) - WIDTH) / 2,
            (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );
 
        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
 
        glfwShowWindow(window);
    }
    
    public static boolean isKeyDown(int key){
    	return GLFW.glfwGetKey(window, key) == GLFW.GLFW_PRESS;
    }

    public static boolean isKeyUp(int key){
    	return GLFW.glfwGetKey(window, key) == GLFW.GLFW_RELEASE;
    }
    
    private void Input(){
    	if(isKeyDown(87))
    		System.out.println("W Key has been pressed");
    }
    
    private void loop() {
        GLContext.createFromCurrent();
 
        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        while ( glfwWindowShouldClose(window) == GL_FALSE ) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            
            Input();
            
            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }
 
    public static void main(String[] args) {
        new Driver().execute();
    }
}