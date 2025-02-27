package cirno.cards

import cirno.Cirno.Statics.makeID
import cirno.abstracts.CirnoCard
import cirno.characters.spellZones
import com.megacrit.cardcrawl.cards.AbstractCard
import com.megacrit.cardcrawl.characters.AbstractPlayer
import com.megacrit.cardcrawl.core.CardCrawlGame
import com.megacrit.cardcrawl.localization.CardStrings
import com.megacrit.cardcrawl.monsters.AbstractMonster
import cirno.interfaces.Helper


class DamageForEachSpell : CirnoCard(cardStrings, COST, TYPE, RARITY, TARGET, DAMAGE_UP, BLOCK_UP, BONUS_UP, COST), Helper {

    init {
        baseDamage = DAMAGE
        baseBlock = BLOCK
        magicNumber = BONUS
        baseMagicNumber = magicNumber
    }

    override fun use(p: AbstractPlayer, m: AbstractMonster?) {
        damage(m!!, cirnoDynamicNumber)
    }

    override fun applyPowers() {
        if (CardCrawlGame.dungeon != null) {
            val placeHolderDamage = baseDamage
            cirnoDynamicNumber = baseDamage + magicNumber * player.spellZones.storedCardsSize()
            baseDamage = cirnoDynamicNumber
            super.applyPowers()
            cirnoDynamicNumber = damage
            baseDamage = placeHolderDamage
            isDamageModified = damage != baseDamage;
        }
    }

    override fun calculateCardDamage(mo: AbstractMonster?) {
        if (CardCrawlGame.dungeon != null) {
            val placeHolderDamage = baseDamage
            cirnoDynamicNumber = baseDamage + magicNumber * player.spellZones.storedCardsSize()
            baseDamage = cirnoDynamicNumber
            super.calculateCardDamage(mo)
            cirnoDynamicNumber = damage
            baseDamage = placeHolderDamage
            isDamageModified = damage != baseDamage;
        }
    }

    companion object {
        private val cardStrings = CardCrawlGame.languagePack.getCardStrings(
                makeID(DamageForEachSpell::class.java.simpleName))
        private val COST = 1
        private val TYPE = CardType.ATTACK
        private val RARITY = AbstractCard.CardRarity.COMMON
        private val TARGET = AbstractCard.CardTarget.ENEMY
        private val DAMAGE_UP = 3
        private val BLOCK_UP = 0
        private val BONUS_UP = 1
        private val DAMAGE = 6
        private val BLOCK = 0
        private val BONUS = 2
    }
}
