package items;

import characters.Hero;

public interface Equippable {
    void equip(Hero player);
    void unEquip(Hero player);
}
