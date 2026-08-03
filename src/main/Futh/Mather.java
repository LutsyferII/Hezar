package main.Futh;

import main.Settings;

public class Mather {
	public static Double calcDamage(Double raw_damage,Double armor) {
		Double damage = 0.0;

		Double armor_coef = 1.0;
		if(armor>30.0) {
			armor_coef = armor/30;
		}
		Double new_armor = armor*armor_coef;
		Settings.debug("{DAMAGE TEST}chenged armor "+new_armor);
		damage = raw_damage *(1-(new_armor/(raw_damage*5+new_armor)));
		return damage;
	}
}
