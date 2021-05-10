<<<<<<< HEAD
=======
<<<<<<<< HEAD:src/main/java/model/card/MagicCardHolder.java
package model.card;

public class MagicCardHolder extends CardHolder {

    public MagicCardHolder(Card card, CardState cardState) {
        super(card, cardState);
        //TODO Auto-generated constructor stub
    }

    protected void recalculateEffect() {
        // TODO Auto-generated method stub
        
    }
    
}
========
>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f
package model.card;

public class MagicCardHolder extends CardHolder {

    public MagicCardHolder(MagicCard card, CardState cardState) {
        super(card, cardState);
        //TODO Auto-generated constructor stub
    }

    protected void recalculateEffect() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void endPhase() {
        // TODO Auto-generated method stub
        
    }
    
}
<<<<<<< HEAD
=======
>>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f:src/model/card/MagicCardHolder.java
>>>>>>> 5e5eee32427d7f77560304f1f93115cfebdfb50f
