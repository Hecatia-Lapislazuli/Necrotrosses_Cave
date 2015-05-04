/**
 * 
 */
package com.wegames.engine.spell;

import com.wegames.engine.entity.Mob;

/**
 * @author kevinharty
 *
 */
public abstract class Spell {
	
	int damage;
	
	Mob caster;
	
	SpellObj spellObj;
	
	/**
	 * 
	 */
	public Spell(Mob caster) {
		this.caster = caster;
	}
	void castAt(int x, int y) {
		spellObj.send(caster.x, caster.y, x, y);
	}
}
