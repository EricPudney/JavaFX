package items;

import characters.Hero;

public interface MagicItem {
        void applyBuff(Hero player);
        void removeBuff(Hero player);
}
