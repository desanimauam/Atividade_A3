package aula02.cena;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Cena implements GLEventListener {
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private GLU glu;
    
    // Variáveis do bastão
    public float paddlePosition = 0;
    public float paddleWidth = 80;
    public float paddleHeight = 10;
    
//    Velocidade do bastao
    public float paddleSpeed = 10;
    
    // Variáveis da bola
    private float ballPositionX = 0;
    private float ballPositionY = 0;
    private float ballVelocityX = 1;
    private float ballVelocityY = 1;
    private float ballSize = 20;
    
    // Variáveis da janela de visualização
    public int screenWidth = 1600;
    public int screenHeight = 1000;
    
    public boolean gamePaused = true;

    @Override
    public void init(GLAutoDrawable drawable) {
        // Dados iniciais da cena
        glu = new GLU();

        // Estabelece as coordenadas do SRU (Sistema de Referencia do Universo)
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 2;
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        // Obtem o contexto OpenGL
        GL2 gl = drawable.getGL().getGL2();
        configuraDisplay(gl);
        
        // Desenha o bastão
        gl.glColor3f(1, 1, 1);
        gl.glTranslatef(paddlePosition, 10, 0);
        gl.glRectf(0, 0, paddleWidth, paddleHeight);

        // Desenha a bola
        gl.glColor3f(1, 1, 1);
        gl.glTranslatef(ballPositionX, ballPositionY, 0);
        gl.glRectf(-ballSize / 2, -ballSize / 2, ballSize / 2, ballSize / 2);

        // Atualiza a posição do bastão
        gl.glPushMatrix();
        updatePaddlePosition();
        gl.glPopMatrix();

        // Atualiza a posição da bola
        gl.glPushMatrix();
        updateBallPosition();
        gl.glPopMatrix();
        
        // Atualiza a posição da bola
        if (!gamePaused) {
            ballPositionX += ballVelocityX;
            ballPositionY += ballVelocityY;
        }

        // Verifica colisão com as paredes
        if (ballPositionX - ballSize / 2 < 0 || ballPositionX + ballSize / 2 > screenWidth) {
            ballVelocityX *= -1; // Inverte a direção horizontal da bola
        }
        if (ballPositionY + ballSize / 2 > screenHeight) {
            ballVelocityY *= -1; // Inverte a direção vertical da bola
        }

        // Verifica colisão com o bastão
        if (ballPositionY - ballSize / 2 < paddleHeight && ballPositionX + ballSize / 2 > paddlePosition && ballPositionX - ballSize / 2 < paddlePosition + paddleWidth) {
            ballVelocityY *= -1; // Inverte a direção vertical da bola
        }

        // Desenha o placar e vidas na tela

        // ...

        gl.glFlush();

        cordenadas(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        // Obtenha o contexto gráfico OpenGL
        GL2 gl = drawable.getGL().getGL2();

        gl.glViewport(0, 0, width, height);
        
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0, width, 0, height);
        
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
    
    public void configuraDisplay(GL2 gl){
        // Define a cor de fundo da janela (R, G, G, alpha)
        gl.glClearColor(0, 0, 0, 1);
        // Limpa a janela com a cor especificada
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        // Carrega a matriz identidade
        gl.glLoadIdentity();
    }
    
    public void cordenadas(GL2 gl){
        gl.glColor3f(1, 0, 1);
        gl.glLineWidth(1);

        // Linhas horizontais
        for (float y = -1.0f; y <= 1.0f; y += 0.1f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(-1.0f, y);
            gl.glVertex2f(1.0f, y);
            gl.glEnd();
        }

        // Linhas verticais
        for (float x = -1.0f; x <= 1.0f; x += 0.1f) {
            gl.glBegin(GL2.GL_LINES);
            gl.glVertex2f(x, -1.0f);
            gl.glVertex2f(x, 1.0f);
            gl.glEnd();
        }
    }
    
    private void updatePaddlePosition() {
        // Verifica se a nova posição do bastão ultrapassa as extremidades da tela
        if (paddlePosition < paddleWidth / 2) {
            paddlePosition = paddleWidth / 2;
        } else if (paddlePosition > screenWidth - paddleWidth / 2) {
            paddlePosition = screenWidth - paddleWidth / 2;
        }
    }

    private void updateBallPosition() {
        // Atualiza a posição da bola de acordo com a velocidade
        ballPositionX += ballVelocityX;
        ballPositionY += ballVelocityY;

        // Verifica se a bola atinge as extremidades da tela
        if (ballPositionX < ballSize / 2) {
            ballPositionX = ballSize / 2;
            ballVelocityX *= -1; // Inverte a direção horizontal da bola
        } else if (ballPositionX > screenWidth - ballSize / 2) {
            ballPositionX = screenWidth - ballSize / 2;
            ballVelocityX *= -1; // Inverte a direção horizontal da bola
        }

        if (ballPositionY < ballSize / 2) {
            ballPositionY = ballSize / 2;
            ballVelocityY *= -1; // Inverte a direção vertical da bola
        } else if (ballPositionY > screenHeight - ballSize / 2) {
            ballPositionY = screenHeight - ballSize / 2;
            ballVelocityY *= -1; // Inverte a direção vertical da bola
        }
    }
}
