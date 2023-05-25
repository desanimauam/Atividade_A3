package aula02.input;

import aula02.cena.Cena;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
/**
 *
 * @author Kakugawa
 */
public class KeyBoard implements KeyListener{
    private Cena cena;
    
    public KeyBoard(Cena cena){
        this.cena = cena;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {        
        System.out.println("Key pressed: " + e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
            System.exit(0);

        // Movimento do bastão para a direita
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
        cena.paddlePosition += cena.paddleSpeed;
        // Verifique se o bastão ultrapassou a extremidade direita da tela
        if (cena.paddlePosition > cena.screenWidth / 2 - cena.paddleWidth / 2) {
            cena.paddlePosition = cena.screenWidth / 2 - cena.paddleWidth / 2;
        }
    }

        // Movimento do bastão para a esquerda
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            cena.paddlePosition -= cena.paddleSpeed;
            // Verifique se o bastão ultrapassou a extremidade esquerda da tela
            if (cena.paddlePosition < -cena.screenWidth / 2 + cena.paddleWidth / 2) {
                cena.paddlePosition = -cena.screenWidth / 2 + cena.paddleWidth / 2;
            }
        }
        
        // Pausar o jogo
        if (e.getKeyCode() == KeyEvent.VK_P) {
            cena.gamePaused = !cena.gamePaused;
        }
    }
    

    @Override
    public void keyReleased(KeyEvent e) { }

}
