/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.plsql.ast;

import net.sourceforge.pmd.annotation.InternalApi;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.ast.impl.javacc.AbstractJjtreeNode;
import net.sourceforge.pmd.lang.symboltable.Scope;

@InternalApi
public abstract class AbstractPLSQLNode extends AbstractJjtreeNode<PLSQLNode> implements PLSQLNode {
    protected Object value;
    protected PLSQLParser parser;
    protected Scope scope;

    AbstractPLSQLNode(int i) {
        super(i);
    }

    @Override // override to make protected member accessible to parser
    protected void setImage(String image) {
        super.setImage(image);
    }

    protected void jjtSetValue(Object value) {
        this.value = value;
    }

    public Object jjtGetValue() {
        return value;
    }

    @Override
    public Object jjtAccept(PLSQLParserVisitor visitor, Object data) {
        return visitor.visit(this, data);
    }


    @Override
    public String getXPathNodeName() {
        return PLSQLParserImplTreeConstants.jjtNodeName[id];
    }

    /*
     * You can override these two methods in subclasses of SimpleNode to
     * customize the way the node appears when the tree is dumped. If your
     * output uses more than one line you should override toString(String),
     * otherwise overriding toString() is probably all you need to do.
     */
    public String toString(String prefix) {
        return prefix + toString();
    }

    @Override
    public String toString() {
        return getXPathNodeName();
    }

    /*
     * Override this method if you want to customize how the node dumps out its
     * children.
     */

    public void dump(String prefix) {
        System.out.println(toString(prefix));
        for (Node child : children) {
            AbstractPLSQLNode n = (AbstractPLSQLNode) child;
            if (n != null) {
                n.dump(prefix + " ");
            }
        }
    }

    /**
     * Return node image converted to the normal Oracle form.
     *
     * <p>
     * Normally this is uppercase, unless the names is quoted ("name").
     * </p>
     */
    public String getCanonicalImage() {
        return PLSQLParserImpl.canonicalName(this.getImage());
    }

    /**
     * Convert arbitrary String to normal Oracle format, under assumption that
     * the passed image is an Oracle name.
     *
     * <p>
     * This a helper method for PLSQL classes dependent on SimpleNode, that
     * would otherwise have to import PLSQParser.
     * </p>
     *
     * @param image
     * @return
     */
    public static String getCanonicalImage(String image) {
        return PLSQLParserImpl.canonicalName(image);
    }

    @Override
    public Scope getScope() {
        if (scope == null) {
            return getParent().getScope();
        }
        return scope;
    }

    @Override
    public void setScope(Scope scope) {
        this.scope = scope;
    }
}
