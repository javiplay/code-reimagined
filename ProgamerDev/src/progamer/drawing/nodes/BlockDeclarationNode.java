package progamer.drawing.nodes;

import org.eclipse.swt.graphics.Rectangle;

import progamer.drawing.SpriteProvider;



public class BlockDeclarationNode extends JavaMarioNode {

	public BlockDeclarationNode(String name, int nodeType, int offset) {
		super(name, nodeType, offset);
		// TODO Auto-generated constructor stub
	}

	Rectangle getBlockDeclarationSprites(JavaMarioNode elem, int x, int y) {
		
		Rectangle boundingBox = new Rectangle(x, y, 0, 0);
		Rectangle childBox = null;
		
		for (JavaMarioNode child : elem.children) {
			childBox = child.getSprites(x, y);
			boundingBox = SpriteProvider.getUnionBox(boundingBox, childBox);
			x += childBox.width;						
		}
		elem.rectangle = boundingBox;
		
		return boundingBox;
	}


	@Override
	public Rectangle getSprites(int x, int y) {
		return getBlockDeclarationSprites(this, x, y); 		
	}
}
