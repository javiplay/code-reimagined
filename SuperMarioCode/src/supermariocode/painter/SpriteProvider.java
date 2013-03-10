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

import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class SpriteProvider {

	public SpriteProvider() {

	}

	public SpriteComposite field(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.strangeYellowBox, 0, 1);
		return comp;
	}

	public SpriteComposite expression(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.normalYellowBox, 0, 2);
		return comp;
	}

	public SpriteComposite local(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.questionYellowBox, 0, 1);
		return comp;
	}

	public SpriteComposite constructorLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainLeftSide, 0, 0);
		comp.addSprite(comp.landTerrainLeftSideUp, 0, 1);
		return comp;
	}

	public SpriteComposite constructorCenter(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrain, 0, 0);
		comp.addSprite(comp.landTerrainSoil, 0, 1);
		return comp;
	}

	public SpriteComposite constructorRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landTerrainRightSide, 0, 0);
		comp.addSprite(comp.landTerrainRightSideUp, 0, 1);
		return comp;
	}

	public SpriteComposite ifLeft(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.landMountainLeftSide, 1, 0);
		comp.addSprite(comp.landMountainLeftSideUp, 1, 1);
		return comp;
	}

	public SpriteComposite ifCenter(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		comp.addSprite(comp.landMountainSoil, 0, 1);
		return comp;
	}

	public SpriteComposite mountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountain, 0, 0);
		return comp;
	}

	public SpriteComposite leftMountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainLeftSide, 0, 0);
		return comp;
	}

	public SpriteComposite rightMountain(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		return comp;
	}

	public SpriteComposite ifRight(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.landMountainRightSide, 0, 0);
		comp.addSprite(comp.landMountainRightSideUp, 0, 1);
		comp.addSprite(comp.noneSprite, 1, 0);

		return comp;
	}

	public SpriteComposite ret(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.doorBrownDownSide, 1, 0);
		comp.addSprite(comp.doorBrownUpSide, 1, 1);
		return comp;
	}

	public SpriteComposite forTube(int x, int y) {
		SpriteComposite comp = new SpriteComposite(x, y);
		comp.addSprite(comp.noneSprite, 0, 0);
		comp.addSprite(comp.tubeGreenLeftSideDown, 1, 0);
		comp.addSprite(comp.tubeGreenLeftSideUp, 1, 1);
		comp.addSprite(comp.tubeGreenRihtSideDown, 2, 0);
		comp.addSprite(comp.tubeGreenRightSideUp, 2, 1);
		comp.addSprite(comp.noneSprite, 3, 0);

		return comp;
	}

	public Rectangle getSprites(JavaMarioNode elem, int x, int y) {
		System.out.println(elem.name + "  Posici�n: " + x + " " + y);
		// Devolver el tama�o total de la composici�n
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		ArrayList<JavaMarioNode> list = elem.children;

		SpriteComposite comp;

		switch (elem.getNodeType()) {

		case ASTNode.TYPE_DECLARATION:
			for (JavaMarioNode child : elem.children) {
				if (child.getNodeType() == ASTNode.METHOD_DECLARATION) {
					Rectangle r = getSprites(child, x, y);
					x += r.width;
					boundingBox.width += r.width;
					if (r.height > boundingBox.height) {
						boundingBox.height = r.height;
					}
				}
			}

			return boundingBox;

		case ASTNode.FIELD_DECLARATION:
			comp = field(x, y);
			boundingBox.x += comp.width; // lenx
			if (comp.height > boundingBox.y) {
				boundingBox.y = comp.height; // leny
			}
			elem.addComposite(comp);
			x += comp.width;
			break;

		case ASTNode.METHOD_DECLARATION:
			JavaMarioNode methodBlock = null;

			// coger la lista de block
			for (JavaMarioNode child : list) {
				if (child.getNodeType() == ASTNode.BLOCK) {
					methodBlock = child;
					break;
				}
			}

			comp = constructorLeft(x, y);
			elem.addComposite(comp);

			x += comp.width;
			y += comp.height;

			Rectangle methodBox = new Rectangle(0, 0, 0, 0);

			methodBox = getSprites(methodBlock, x, y);
			System.out.println("METHOD:" + methodBox.width + "x"
					+ methodBox.height);

			y -= 2;
			int iterx = x;
			while (iterx < x + methodBox.width) {
				comp = constructorCenter(iterx, y);
				elem.addComposite(comp);
				iterx++;
			}
			x = iterx;

			comp = constructorRight(x, y);
			elem.addComposite(comp);

			// establecemos el tama�o final de esta composici�n de sprites
			boundingBox.width += methodBox.width + 2 * comp.width + 2; // el
																		// contenido
																		// horizontal
																		// del
			// metodo mas el cuadro de
			// inicio y el de final
			if (comp.height + methodBox.height > boundingBox.height) {
				boundingBox.height = comp.height + methodBox.height; // leny
			}

			System.out.println(boundingBox);
			elem.rectangle = boundingBox;
			return boundingBox;

		case ASTNode.BLOCK:
			Rectangle childBox = null;

			for (JavaMarioNode child : elem.children) {
				childBox = getSprites(child, x, y);
				x += childBox.width;
				if (childBox.height > boundingBox.height) {
					boundingBox.height = childBox.height;
				}
				boundingBox.width += childBox.width;
			}

			elem.rectangle = boundingBox;

			System.out.println(boundingBox);
			return boundingBox;

		case ASTNode.EXPRESSION_STATEMENT:

			comp = expression(x, y);
			boundingBox.x += comp.width; // lenx
			if (comp.height > boundingBox.height) {
				boundingBox.height = comp.height; // leny
			}
			elem.addComposite(comp);
			x += comp.width;
			break;

		case ASTNode.VARIABLE_DECLARATION_STATEMENT:

			comp = local(x, y);
			boundingBox.width += comp.width; // lenx
			if (comp.height > boundingBox.height) {
				boundingBox.height = comp.height; // leny
			}
			elem.addComposite(comp);
			elem.rectangle = boundingBox;
			System.out.println(boundingBox);
			return boundingBox;

		case ASTNode.RETURN_STATEMENT:
			comp = ret(x, y);
			boundingBox.x += comp.width; // lenx
			if (comp.y + comp.height > boundingBox.y) {
				boundingBox.y = comp.y + comp.height; // leny
			}
			elem.addComposite(comp);
			x += comp.width;
			break;

		case ASTNode.IF_STATEMENT:

			int initx = x;
			int inity = y;

			JavaMarioNode elseNode = elem.children.get(2);

			Rectangle elseBox = getSprites(elseNode, x, y);

			JavaMarioNode thenNode = elem.children.get(1);
			comp = ifLeft(x, y);
			elem.addComposite(comp);

			x += comp.width;
			y += comp.height;

			Rectangle thenBox = new Rectangle(0, 0, 0, 0); // the size of the
															// corresponding
			// part of stage
			int soilLength = 0;

			thenBox = getSprites(thenNode, x, y);
			System.out.println("THEN:" + thenBox.x + "x" + thenBox.y);

			y -= 2;
			iterx = x;
			soilLength = (thenBox.width > elseBox.width) ? thenBox.width : elseBox.width;

			while (iterx < x + soilLength) {
				comp = ifCenter(iterx, y);
				elem.addComposite(comp);
				iterx++;
			}
			x = iterx;

			// final terrain
			comp = ifRight(x, y);
			x += comp.width;
			elem.addComposite(comp);

			// rellenamos el fondo del else
			for (int posx = 0; posx < soilLength; posx++) {
				for (int posy = 0; posy < elseBox.y - 2; posy++) {
					comp = mountain(initx + 2 + posx, inity + posy);
					elem.addComposite(0, comp);
				}
			}
			for (int posy = 0; posy < elseBox.y - 2; posy++) {
				comp = leftMountain(initx + 1, inity + posy);
				elem.addComposite(0, comp);
			}
			for (int posy = 0; posy < elseBox.y - 2; posy++) {
				comp = rightMountain(initx + soilLength + 2, inity + posy);
				elem.addComposite(0, comp);
			}

			// ajustar
			boundingBox.width += soilLength + 4;
			if (y + thenBox.height > boundingBox.height) {
				boundingBox.height = y + thenBox.height; // leny
			}
			
			return boundingBox;

		case ASTNode.FOR_STATEMENT:

			// list = FindBlock(list);
			// initial constructor/method terrain
			comp = forTube(x, y);
			int firsttubesizex = comp.width;
			elem.addComposite(comp);
			x += comp.width;
			y += comp.height;
			Rectangle forLength = new Rectangle(0, 0, 0, 0);
			forLength = getSprites(list.get(0), x, y);
			System.out.println("FOR:" + forLength.x + "x" + forLength.y);

			x += forLength.x;
			y -= comp.height;
			comp = forTube(x, y);
			elem.addComposite(comp);

			// Start painting floor inside for loop
			x -= forLength.x;
			int soilx = x - 1; // pointer to start painting the floor
			// comp = constructorLeft(x-1, y);
			// elem.addComposite(comp);
			while (soilx <= x + forLength.x) {
				comp = constructorCenter(soilx, y);
				elem.addComposite(comp);
				soilx++;
			}
			// x = soilx;

			x += soilx + 4; // incremento para poder seguir pintando tras el
							// segundo tubo, 4 es el ancho del tubo

			boundingBox.x += forLength.x + 8;
			if (y + forLength.y > boundingBox.y) {
				boundingBox.y = y + forLength.y; // leny
			}
			break;
		default:

			return getSprites(list.get(0), x, y);

		}

		return boundingBox;
	}

	/*
	 * private JavaMarioNode FindBlock(JavaMarioNode list) { int i = 0; boolean
	 * found = false; ArrayList blockList = null; while (i<list.size() &&
	 * !found) { JavaMarioNode n = (JavaMarioNode) list.get(i); if (n.nodeType
	 * == ASTNode.BLOCK) { found = true; blockList = (ArrayList) list.get(i+1);
	 * } i+=2; } return blockList; }
	 */
	private ArrayList getThenList(ArrayList list) {

		ArrayList blockList = null;
		JavaMarioNode n = (JavaMarioNode) list.get(2);

		if (n.getNodeType() != ASTNode.BLOCK) {
			blockList = new ArrayList();
			blockList.add(list.get(2));
			blockList.add(list.get(3));
		} else {
			blockList = (ArrayList) list.get(3);
		}

		return blockList;
	}

	private ArrayList getElseList(ArrayList list) {

		ArrayList blockList = null;

		// si no tiene parte else la lista tiene 4 elementos (expresion,
		// listaExpresion, then, listaThen)
		// La parte else siempre esta en la posici�n 4, si es un block se
		// devuelve el contenido del block (la lista en la posici�n 5) si no,
		// se crea una lista y se mete el 4 y el 5.

		if (list.size() == 4) {
			return blockList;
		} else {
			JavaMarioNode n = (JavaMarioNode) list.get(4);

			if (n.getNodeType() != ASTNode.BLOCK) {
				blockList = new ArrayList();
				blockList.add(list.get(4));
				blockList.add(list.get(5));
			} else {
				blockList = (ArrayList) list.get(5);
			}
			return blockList;
		}

	}
}
