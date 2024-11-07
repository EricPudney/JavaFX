package items;

import characters.Hero;


public class AttackBuffItem extends Item implements MagicItem, Equippable {
    double buff;

    static String[][] attackItemList = {
        {"Hurty ring", "A ring that makes your enemies bleed faster!"},
        {"Ouchy stone", "An enchanted stone that increses your damage output!"},
        {"Weapon salve", "A magical preparation that increases your damage!"}
    };

    public AttackBuffItem(){
        super();
    }

    public static AttackBuffItem generateAttackBuffItem() {
        AttackBuffItem item = new AttackBuffItem();
        int index = rng.nextInt(attackItemList.length -1);
        item.name = attackItemList[index][0];
        item.description = attackItemList[index][1];
        item.buff = 1;
        item.value = 20;
        return item;
    }

    public void applyBuff(Hero player) {
        player.attack += (int) buff;
    }

    public void removeBuff(Hero player) {
        player.attack -= (int) buff;
    }

    @Override
    public void equip(Hero player) {
        System.out.printf("You have equipped the %s!\n", name);
        applyBuff(player);
    }

    @Override
    public void unEquip(Hero player) {
        System.out.printf("You have unequipped the %s!\n", name);
        removeBuff(player);
    }
}
