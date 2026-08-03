package main.toch;

import org.bukkit.configuration.ConfigurationSection;

public class TochStat{
	public String type;
	public boolean percent = false;
	public String name = "Unname";
	public Double value=0.0;
	public int perlevel=0;
	public Double preval=0.0;
	public int preperlvl=0;
	public String fr = "PWEAPON";
	public TochStat(ConfigurationSection c){
		type=c.getString("type");
		if(c.contains("value")) {
			value=c.getDouble("value");
			preval=c.getDouble("value");
		}
		if(c.contains("perlevel")) {
			perlevel=c.getInt("perlevel");
			preperlvl=c.getInt("perlevel");
		}
		if(c.contains("name")) {
			name=c.getString("name");
		}
		if(c.contains("preval")) {
			preval=c.getDouble("preval");
		}
		if(c.contains("preperlvl")) {
			preperlvl = c.getInt("preperlvl");
		}
		if(c.contains("for")) {
			fr = c.getString("for");
		}
		if(c.contains("percent")&&c.isBoolean("percent")) {
			percent = c.getBoolean("percent");
		}
		
	}
	public Double getPercentPreValue(double orig) {
		
		return orig * (1/(100/preperlvl+1));  
		
	}

	public Double getPercentFinalValue(double orig) {
		
		
		return orig/100*perlevel;//1+0.01*120
	}

	public Double getPercentFullValue(double orig, int level, int min) {
		Double full = 0.0;
		level-=min;
		for(int i = 0;i<level;i++) {
			full+=this.value+(this.value/100*(this.perlevel*i));
		}
		return full;
	}
	
	public Double getPreValue(int min){
		
		return (Double) this.preval+(this.preval/100*(this.preperlvl*(min-1)));//
		
	}
	public Double getFinalValue(int level) {
		return (Double) this.value+(this.value/100*(this.perlevel*(level-1)));//1+0.01*120
	}
	public Double getFullValue(int level, int min) {
		Double full = 0.0;
		level-=min;
		for(int i = 0;i<level;i++) {
			full+=this.value+(this.value/100*(this.perlevel*i));
		}
		return full;
	}
	
}