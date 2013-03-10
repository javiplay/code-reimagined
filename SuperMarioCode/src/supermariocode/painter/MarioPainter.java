/* 
  Code Reimagined, Class for painting the AST
    Copyright (C) 2010-2013 José Javier Asensio Montiel

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    */
package supermariocode.painter;


import java.net.URL;
import java.util.ArrayList;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.*;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import supermariocode.views.MarioCodeView;

public class MarioPainter  {
	
	private static ArrayList tree;
	
	private static int scale = 16;
	
	
	private int base;
	public Image img;
	public Image imgBG;
	public GC g;
	
	//Para a�adir colores a los rectangulos:
	Device d;
	Color magenta;
	Color green;
	Color yellow;
	Color red;
	Color black;
	Color cyan;
	
	ArrayList colores;
	
	
	public MarioPainter(int base, GC g) {
		
        img = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("smwtileset.gif"));
        imgBG = new Image(MarioCodeView.myCanvas.getDisplay(),
    			MarioCodeView.class.getResourceAsStream("background1.jpg"));
        this.base = base;
        this.g = g;
        
        //Para incluir colores a los rectangulos:
        d = g.getDevice();
        //Definimos colores:
        magenta = d.getSystemColor(SWT.COLOR_MAGENTA);
        green = d.getSystemColor(SWT.COLOR_DARK_GREEN);
        yellow = d.getSystemColor(SWT.COLOR_YELLOW);
        red = d.getSystemColor(SWT.COLOR_RED);
        black = d.getSystemColor(SWT.COLOR_BLACK);
        cyan = d.getSystemColor(SWT.COLOR_CYAN);
        
        //A�adimos a un array los diferentes colores:
        colores = new ArrayList();
        colores.add(black);
        colores.add(cyan);
        colores.add(green);
        colores.add(red);
        colores.add(magenta);
        colores.add(yellow);
        
       // g.drawImage(imgBG, 0, 0);
 
	}
	
	public void paintTree(JavaMarioNode mn) {
		System.out.println( mn.name);
			ArrayList<JavaMarioNode> content = mn.children;
			System.out.println( mn.compList);
			for (SpriteComposite sc: mn.compList) {
				
				for (Sprite s: sc.spriteList) {
					g.drawImage(img, s.x, s.y, scale, scale, s.posx*scale, base - (s.posy+1)*scale, scale, scale);
				}
			}
			for (JavaMarioNode child: content){
				paintTree(child);
			}
					
	}
	
	
	//M�todo que agrega rectangulos para el modo debug:
	public void paintTreeDebug(JavaMarioNode mn) {
		
			
		g.drawRectangle(mn.rectangle.x*scale, base - (mn.rectangle.y + mn.rectangle.height)*scale, 
				mn.rectangle.width*scale, mn.rectangle.height*scale);

			/*for (SpriteComposite sc: node.compList) {
				//Tomamos el spritecomposite para obtener las coordenadas para la generaci�n de rectangulos:
				System.out.println(sc.spriteList);
				System.out.println(sc);
				
				//g.drawRectangle(sc.posx*scale, base - (sc.posy+sc.leny)*scale, sc.lenx*scale, sc.leny*scale);
			     
				//g.setLineStyle(SWT.LINE_DOT);
				/*g.setLineWidth(2);
				g.setForeground((Color) colores.get(index));
				index++;
				if(index > colores.size()){
					index = 1;
				}*/
			
			for (JavaMarioNode child: mn.children){
					paintTreeDebug(child);
			}			
		
	}

}
