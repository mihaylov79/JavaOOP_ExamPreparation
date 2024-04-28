package fairyShop.models;

import java.util.Collection;

public class ShopImpl implements Shop{


    @Override
    public void craft(Present present, Helper helper) {

        if (helper.canWork()){
            helper.getInstruments().forEach(instrument -> {
                while (!instrument.isBroken() && helper.canWork()){
                    helper.work();
                    present.getCrafted();
                    instrument.use();

                    if (present.isDone())
                        return;

                    if (!helper.canWork())
                        return;
                }


            });
        }



//        if(helper.getInstruments().stream().iterator().hasNext() && helper.canWork()){
//            helper.work();
//            while(helper.getInstruments().)
//        }

    }
}
