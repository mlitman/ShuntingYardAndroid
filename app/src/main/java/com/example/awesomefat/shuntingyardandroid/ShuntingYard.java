package com.example.awesomefat.shuntingyardandroid;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * Created by awesomefat on 4/19/16.
 */
public class ShuntingYard
{
    private LinkedList<String> outputQ = new LinkedList<String>();
    private LinkedList<String> inputQ = new LinkedList<String>();
    private Stack<String> opStack = new Stack<String>();

    public ShuntingYard(String postFix)
    {
        StringTokenizer st = new StringTokenizer(postFix, "+-*/^()", true);
        while(st.hasMoreTokens())
        {
            this.inputQ.addLast(st.nextToken().trim());
        }
    }

    private int opPriority(String op)
    {
        if(op.equals("+") || op.equals("-"))
        {
            return 2;
        }
        else if(op.equals("*") || op.equals("/"))
        {
            return 3;
        }
        else
        {
            return 4;
        }
    }

    private boolean isOp(String symbol)
    {
        return "+-*/^()".indexOf(symbol) >= 0;
    }

    private double applyOp(double num1, double num2, String op)
    {
        if(op.equals("+"))
        {
            return num1 + num2;
        }
        else if(op.equals("-"))
        {
            return num1 - num2;
        }
        else if(op.equals("*"))
        {
            return num1 * num2;
        }
        else if(op.equals("/"))
        {
            return num1 / num2;
        }
        else
        {
            return Math.pow(num1, num2);
        }
    }

    private String doMath()
    {
        double num1;
        double num2;
        Stack<String> mathStack = new Stack<String>();
        for(String s : this.outputQ)
        {
            if(this.isOp(s))
            {
                num2 = Double.parseDouble(mathStack.pop());
                num1 = Double.parseDouble(mathStack.pop());
                mathStack.push("" + this.applyOp(num1, num2, s));
            }
            else
            {
                mathStack.push(s);
            }
        }
        return mathStack.pop();
    }

    public String getAnswer()
    {
        String currSymbol;
        while(this.inputQ.size() > 0)
        {
            currSymbol = this.inputQ.removeFirst();
            if(this.isOp(currSymbol))
            {
                //we have an op, what do we do with it?
                if(currSymbol.equals("("))
                {
                    this.opStack.push(currSymbol);
                }
                else if(currSymbol.equals(")"))
                {
                    //clear the op stack to the ( and add to outputQ
                    String currOp;
                    while(!this.opStack.empty())
                    {
                        currOp = this.opStack.pop();
                        if(currOp.equals("("))
                        {
                            break;
                        }
                        else
                        {
                            this.outputQ.addLast(currOp);
                        }
                    }
                }
                else if(currSymbol.equals("^"))
                {
                    this.opStack.push(currSymbol);
                }
                else
                {
                    //must be a normal op
                    //try to add this to the op stack over and over
                    //until it finally fits
                    //****START HERE!!!!
                    while(!this.opStack.empty() && !this.opStack.peek().equals("(") && this.opPriority(this.opStack.peek()) >= this.opPriority(currSymbol))
                    {
                        this.outputQ.addLast(this.opStack.pop());
                    }
                    this.opStack.push(currSymbol);
                }
            }
            else
            {
                //we must be dealing with a number
                this.outputQ.addLast(currSymbol);
            }
        }

        //clear the opStack to the outputQ
        while(!this.opStack.empty())
        {
            this.outputQ.addLast(this.opStack.pop());
        }

        return this.doMath();
    }
}
