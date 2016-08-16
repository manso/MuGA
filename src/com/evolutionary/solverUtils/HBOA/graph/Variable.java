//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////

package com.evolutionary.solverUtils.HBOA.graph;


/**
 * Created on 20/abr/2016, 19:45:14 
 * @author zulu - computer
 */
public class Variable implements IGraph{
	private int variable;
	private IGraph zero, one;
	
	
	public Variable(int x){variable = x;}  								// Default constructor
	public Variable(int x, IGraph zero, IGraph one){
		variable = x;
		if(zero instanceof Leaf)          								// A variable is responsible for setParent()  
			zero.setParent(this,0);       								// to both of its leaves.
		if(one instanceof Leaf)
			one.setParent(this,1);
		this.zero = zero;
		this.one = one;
	}
	
	public int getVariable(){return variable;}
	public IGraph getZero(){return zero;}
	public IGraph getOne(){return one;}
	public void setZero(IGraph zero){this.zero = zero;}
	public void setOne(IGraph one){this.one = one;}
	public void setParent(Variable parent, int side){} 					// No need to store a variable's parent.
		
	public String toString(){return "(X" + variable + " ("+ zero + ")" + " (" + one +"))";}



    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604201945L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}