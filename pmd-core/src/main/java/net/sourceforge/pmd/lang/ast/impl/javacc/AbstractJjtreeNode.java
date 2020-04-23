/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.ast.impl.javacc;

import net.sourceforge.pmd.annotation.Experimental;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.impl.AbstractNode;

/**
 * Base class for node produced by JJTree. JJTree specific functionality
 * present on the API of {@link Node} and {@link AbstractNode} will be
 * moved here for 7.0.0.
 *
 * <p>This is experimental because it's expected to change for 7.0.0 in
 * unforeseeable ways. Don't use it directly, use the node interfaces.
 */
@Experimental
public abstract class AbstractJjtreeNode<N extends JjtreeNode<N>> extends AbstractNode<N> implements JjtreeNode<N> {
    protected final int id;
    private JavaccToken firstToken;
    private JavaccToken lastToken;

    private String image;


    protected AbstractJjtreeNode(int id) {
        super();
        this.id = id;
    }

    @Override
    public String getImage() {
        return image;
    }

    protected void setImage(String image) {
        this.image = image;
    }

    @Override
    public CharSequence getText() {
        String fullText = getFirstToken().document.getFullText();
        return fullText.substring(getStartOffset(), getEndOffset());
    }

    /**
     * This method is called after the node has been made the current node. It
     * indicates that child nodes can now be added to it.
     */
    protected void jjtOpen() {
        // to be overridden
    }

    /**
     * This method is called after all the child nodes have been added.
     */
    protected void jjtClose() {
        // to be overridden
    }

    protected void addChild(AbstractJjtreeNode<N> child, int index) {
        super.addChild(child, index);
    }

    @Override
    public JavaccToken getFirstToken() {
        return firstToken;
    }

    @Override
    public JavaccToken getLastToken() {
        return lastToken;
    }

    // the super methods query line & column, which we want to avoid

    protected void setLastToken(JavaccToken token) {
        this.lastToken = token;
    }

    protected void setFirstToken(JavaccToken token) {
        this.firstToken = token;
    }

    @Override
    public int getBeginLine() {
        return firstToken.getBeginLine();
    }

    @Override
    public int getBeginColumn() {
        return firstToken.getBeginColumn();
    }

    @Override
    public int getEndLine() {
        return lastToken.getEndLine();
    }

    @Override
    public int getEndColumn() {
        return lastToken.getEndColumn();
    }

    /**
     * This toString implementation is only meant for debugging purposes.
     */
    @Override
    public String toString() {
        return "[" + getXPathNodeName() + ":" + getBeginLine() + ":" + getBeginColumn() + "]" + getText();
    }

    private int getStartOffset() {
        return this.getFirstToken().getStartInDocument();
    }

    private int getEndOffset() {
        return this.getLastToken().getEndInDocument();
    }
}
