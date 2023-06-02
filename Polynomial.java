import java.lang.Math;
public class Polynomial {
	double[] coefficient;
	public Polynomial(){
		coefficient= new double[1];
		this.coefficient[0] = 0;
	}
	public Polynomial(double array[]){
		coefficient = array;
		// coefficient = new double[array.length];
		// for(int i=0;i<array.length;i++) {
		// 	this.coefficient[i]= array[i];
		// }
	}
	public Polynomial add(Polynomial p) {
		double[] dummy = new double[Math.max(p.coefficient.length, this.coefficient.length)];
		// for(int j=0;j<dummy.length;j++) {
		// 	dummy[j]=0;
		// }

		Polynomial n= new Polynomial(dummy);
		for(int i=0; i<Math.max(p.coefficient.length, this.coefficient.length); i++) {
			if(i<p.coefficient.length && i<this.coefficient.length)
			{
				dummy[i] = p.coefficient[i] + this.coefficient[i];
			}
			else if(i>=p.coefficient.length && i<this.coefficient.length)
			{
				dummy[i] = this.coefficient[i];
			}
			else if(i<p.coefficient.length && i>=this.coefficient.length)
			{
				dummy[i]=p.coefficient[i];
			}
		}
		//Polynomial privat = new Polynomial(dummy);
		return n;
	}
	public double evaluate(double value) {
		double v=0;
		for(int i=0;i<this.coefficient.length;i++) {
			v=v+this.coefficient[i]*Math.pow(value,i);
		}
		return v;
	}
		
	public boolean hasRoot(double value) {
		double r=evaluate(value);
		if(r==0.0) {
			return true;
		}
		else {
			return false;
		}
	}
}