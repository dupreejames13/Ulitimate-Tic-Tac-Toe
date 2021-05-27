/*/
 * James Dupree, Carson Clymore, Dylan Grafius
 * November 28th, 2019
 * CSC 331
 */

import java.awt.EventQueue;

public class TestUltimateTCT {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UltimateTCT frame = new UltimateTCT();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
