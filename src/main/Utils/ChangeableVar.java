package main.Utils;

public class ChangeableVar<T> {
	T var;
	public ChangeableVar(T n_var){
		this.var = n_var;
	}
	public T getVar() {
		return var;
	}
	public void setVar(T new_var) {
		var = new_var;
	}
}
