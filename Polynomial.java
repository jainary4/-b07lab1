import java.lang.Math;
import java.util.Arrays;
import java.io.*;
public class Polynomial {
	double[] coefficient;
	int[] exponents;
	public Polynomial(){
		coefficient= new double[1];
		exponents= new int[1];
		this.coefficient[0] = 0;
		this.exponents[0] =0;
	}
	public Polynomial(double array[], int[] expo){
		this.coefficient = array;
		// coefficient = new double[array.length];
		// for(int i=0;i<array.length;i++) {
		// 	this.coefficient[i]= array[i];
		// }
		this.exponents = expo;
	}
	public Polynomial(File name) throws IOException{
		BufferedReader reader = new BufferedReader(new FileReader(name));
		String polynomial = reader.readLine();
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String[] term;
		term = polynomial.split("(?=[+-])");

		double[] coefficient = new double[term.length];
		int[] exponent = new int[term.length];

		for (int i = 0; i < term.length; i++) {
			String newterm = term[i];
			int splitIndex = newterm.indexOf('x');

			if (splitIndex != -1) {
				coefficient[i] = Double.parseDouble(newterm.substring(0, splitIndex));
				exponent[i] = Integer.parseInt(newterm.substring(splitIndex + 1));
			} else {
				coefficient[i] = Double.parseDouble(newterm);
				exponent[i]=0; 
			}
		}

		this.coefficient = coefficient;
		this.exponents = exponent;
	}
	public Polynomial add(Polynomial p) {
		int i=0;
		int j=0;
		int k=0;
		int l1= this.coefficient.length;
		int l2= p.coefficient.length;
		double[] dummy = new double[l1+l2];
		int[] dummy_2 = new int[l1+l2];
		while (i<l1 && j<l2){
			if(this.exponents[i]<p.exponents[j]){
				dummy_2[k]=this.exponents[i];
				dummy[k]=this.coefficient[i];
				i++;

			}
			else if (this.exponents[i]>p.exponents[j]){
				dummy_2[k]=p.exponents[j];
				dummy[k]=p.coefficient[j];
				j++;
			}
			else{
				dummy_2[k]=this.exponents[i];
				dummy[k]=this.coefficient[i]+p.coefficient[j];
				i++;
				j++;
			}
			k++;
		}
		while(i<l1){
			dummy[k]= this.coefficient[i];
			dummy_2[k]=this.exponents[i];
			k++;
			i++;
		}

	while(j<l2){
		dummy[k]=this.coefficient[j];
		dummy_2[k]=this.exponents[j];
		k++;
		j++;
	}
	/*int new_size= correct_Size(dummy,k);
	double[] new_dummy = Arrays.copyOf(dummy,new_size);
	int [] new_dummy_2 =  Arrays.copyOf(dummy_2,new_size);*/
	return Trimmer(dummy, dummy_2, l1+l2);
}
private int correct_Size(double[] coefficient,int size){
   int s= size;
   for (int i=size; i>=0; i--){
	if(coefficient[i]==0){
		s=s-1;
	}
	else{
		break;
	}
   }
   return s;
}
public double evaluate(double x){
	double result=0;
	for(int i=0;i<this.coefficient.length;i++){
		result+=this.coefficient[i]*Math.pow(x,this.exponents[i]);
	}
	return result;
}

		
	public boolean hasRoot(double value) {
		double r= evaluate(value);
		if(r==0.0) {
			return true;
		}
		else {
			return false;
		}
	}

/*public Polynomial multiply(Polynomial p){
	double[] dummy = new double[this.coefficient.length*p.coefficient.length];
	int[] dummy_2= new int[this.exponents.length*p.exponents.length];
	for(int i=0; i<this.coefficient.length;i++){
		for(int j=0; j<p.coefficient.length;j++){
			dummy_2[i+j]=p.exponents[j]+this.exponents[i];
			dummy[i+j]+= p.coefficient[j]*this.coefficient[i];
		}
	}
}*/

public Polynomial multiply(Polynomial p) {
		int a = this.exponents.length;
		int b = p.exponents.length;
		int length = a + b + 1;

		double[] dummy = new double[length];
		int[] dummy_2 = new int[length];

		for (int i = 0; i < a; i++) {
			for (int j = 0; j < b; j++) {
				int newExponent = this.exponents[i] + p.exponents[j];
				dummy[newExponent] += (this.coefficient[i] * p.coefficient[j]);
				dummy_2[newExponent] = newExponent;
			}
		}
		Polynomial f = Trimmer(dummy, dummy_2, length);
		return f;
	}

	
		public Polynomial Trimmer(double[] coeffs, int[] powers, int length){
		int newlength = 0;
		double[] coefficient = new double[length];
		int[] exponent = new int[length];
		for (int i = 0; i < length; i++) {
			if (coeffs[i] != 0) {
				coefficient[newlength] = coeffs[i];
				exponent[newlength] = powers[i];
				newlength++;
			}
		}

		coefficient = Arrays.copyOf(coefficient, newlength);
		exponent = Arrays.copyOf(exponent, newlength);

		Polynomial result=  new Polynomial(coefficient, exponent);
		return result;
	}

public void saveToFile(String fileName) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));

		boolean isFirstTerm = true;

		for (int i = 0; i < this.exponents.length; i++) {
			if (this.coefficient[i] != 0) {
				if (!isFirstTerm) {
					writer.write(this.coefficient[i] > 0 ? "+" : "-");
				} else {
					isFirstTerm = false;
				}

				writer.write(Double.toString(Math.abs(this.exponents[i])));

				if (this.exponents[i] > 0) {
					writer.write("x" + this.exponents[i]);
				}
			}
		}

		writer.close();
	}
}
