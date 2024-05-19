package Exe.Ex4.geo;

import java.util.Comparator;

import Exe.Ex4.Ex4_Const;
import Exe.Ex4.GUI_Shapeable;

/**
 * This class represents a Comparator over GUI_Shapes - 
 * as a linear order over GUI_Shapes.
 * Ex4: you should implement this class!
 * @author I2CS
 *
 */
public class ShapeComp implements Comparator<GUI_Shapeable>{
	
	
	public static final Comparator<GUI_Shapeable> CompByToString = new ShapeComp(Ex4_Const.Sort_By_toString);
	
	private int _flag;
	public ShapeComp(int flag) {
		_flag = flag;
		
	}
	@Override
	public int compare(GUI_Shapeable o1, GUI_Shapeable o2) {
		int ans=0;
		if(_flag == Ex4_Const.Sort_By_toString)
			ans = o1.toString().compareTo(o2.toString());
		if(_flag == Ex4_Const.Sort_By_Anti_toString)
			ans = -1*o1.toString().compareTo(o2.toString());
		
		if(_flag == Ex4_Const.Sort_By_Perimeter)
			ans = sign(o1.getShape().perimeter() - o2.getShape().perimeter());
		if(_flag == Ex4_Const.Sort_By_Anti_Perimeter)
			ans = -1*sign(o1.getShape().perimeter() - o2.getShape().perimeter());
		
		if(_flag == Ex4_Const.Sort_By_Area)
			ans = sign(o1.getShape().area() - o2.getShape().area());
		if(_flag == Ex4_Const.Sort_By_Anti_Area)
			ans = -1*sign(o1.getShape().area() - o2.getShape().area());
		
		if(_flag == Ex4_Const.Sort_By_Tag)
			ans = sign(o1.getTag() - o2.getTag());
		if(_flag == Ex4_Const.Sort_By_Anti_Tag)
			ans = -1*sign(o1.getTag() - o2.getTag());
		return ans;
	}
	
	public int sign(double x) {
		if (x > 0)
			return 1;
		if (x < 0)
			return -1;
		return 0;
	}

}
