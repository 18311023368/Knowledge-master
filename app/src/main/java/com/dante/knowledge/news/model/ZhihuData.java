package com.dante.knowledge.news.model;

import com.dante.knowledge.news.other.Data;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Zhihu news class which contains date, stories and tops.
 */
public class ZhihuData extends RealmObject implements Data {

    /**
     * date : 20160129
     * stories : [{"images":["http://pic1.zhimg.com/aef18b16a9a6dcb445d5c235784c25a8.jpg"],"type":0,"id":7813824,"ga_prefix":"012915","title":"运气好的话，说不定 3 万年就把木星挪过来"},{"images":["http://pic2.zhimg.com/3ade66b9adc39db01656baa25ed38775.jpg"],"type":0,"id":7782299,"ga_prefix":"012914","title":"讲真，请不要用眼镜布擦眼镜"},{"images":["http://pic3.zhimg.com/541662b02a6a99a860f7f1b80cd9880e.jpg"],"type":0,"id":7813351,"ga_prefix":"012913","title":"好酱配好面，意面的酱怎么做才好吃？"},{"title":"这俩兄弟自制令人惊叹的玩具，谢耳朵估计也会拜服","ga_prefix":"012912","images":["http://pic3.zhimg.com/129c39a0a941648724dc90ce24e792be.jpg"],"multipic":true,"type":0,"id":7812687},{"images":["http://pic2.zhimg.com/fbf9f9907b48739dcccd6b6a94188d8d.jpg"],"type":0,"id":7812703,"ga_prefix":"012911","title":"一份非常详细的过年囤货指北"},{"images":["http://pic1.zhimg.com/a28113f36de3e7b2e5a2d83b6b385304.jpg"],"type":0,"id":7811123,"ga_prefix":"012910","title":"看完这篇攻略，你也能拍出小王子的小行星"},{"title":"我是一位孕期坚持健身的孕妇，目前我和宝宝都很健康","ga_prefix":"012909","images":["http://pic4.zhimg.com/4a3e3c49433d77ba525a02a5fdb1496f.jpg"],"multipic":true,"type":0,"id":7810014},{"images":["http://pic3.zhimg.com/adb17dd5357b5c4f7e2b83b5ef3b729a.jpg"],"type":0,"id":7805494,"ga_prefix":"012908","title":"海星不止会卖萌，这种凶起来真是「魔鬼杀手」"},{"images":["http://pic1.zhimg.com/d95e4a92692c4b31f57256ce7fb1db5c.jpg"],"type":0,"id":7811235,"ga_prefix":"012907","title":"总觉得感冒是小事，很多大型疫情往往又是流感引起"},{"images":["http://pic1.zhimg.com/660c6c4f33faf14f448263c980ae8e40.jpg"],"type":0,"id":7811643,"ga_prefix":"012907","title":"这些奇葩的电影，光是念出片名来都很羞耻"},{"images":["http://pic3.zhimg.com/a37a9eddf88159cbf461d37e63095fd6.jpg"],"type":0,"id":7809891,"ga_prefix":"012907","title":"味同嚼臭袜子的奶酪，歪果仁民还特别喜欢"},{"images":["http://pic1.zhimg.com/c366908a775629e3eedf0976816e24fc.jpg"],"type":0,"id":7806632,"ga_prefix":"012906","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"http://pic2.zhimg.com/9c8804770cef7efe1b464eb394f73a19.jpg","type":0,"id":7782299,"ga_prefix":"012914","title":"讲真，请不要用眼镜布擦眼镜"},{"image":"http://pic4.zhimg.com/e1d9a422616aceb7ec4525c02e934c43.jpg","type":0,"id":7811643,"ga_prefix":"012907","title":"这些奇葩的电影，光是念出片名来都很羞耻"},{"image":"http://pic3.zhimg.com/6b5d5533831ac8ccfb63948a8b9eb876.jpg","type":0,"id":7812703,"ga_prefix":"012911","title":"一份非常详细的过年囤货指北"},{"image":"http://pic1.zhimg.com/68e424b56afa5117cf425bed6903f52c.jpg","type":0,"id":7809940,"ga_prefix":"012821","title":"20 岁看，几乎什么也不懂；30 岁再看，想哭"},{"image":"http://pic4.zhimg.com/bb6689ab114fde9b75d260728577b95b.jpg","type":0,"id":7810014,"ga_prefix":"012909","title":"我是一位孕期坚持健身的孕妇，目前我和宝宝都很健康"}]
     */
    @PrimaryKey
    private String date;
    /**
     * images : ["http://pic1.zhimg.com/aef18b16a9a6dcb445d5c235784c25a8.jpg"]
     * type : 0
     * id : 7813824
     * ga_prefix : 012915
     * title : 运气好的话，说不定 3 万年就把木星挪过来
     */

    private RealmList<ZhihuItem> stories;
    /**
     * image : http://pic2.zhimg.com/9c8804770cef7efe1b464eb394f73a19.jpg
     * type : 0
     * id : 7782299
     * ga_prefix : 012914
     * title : 讲真，请不要用眼镜布擦眼镜
     */

    private RealmList<ZhihuTop> top_stories;

    public RealmList<ZhihuItem> getStories() {
        return stories;
    }

    public void setStories(RealmList<ZhihuItem> stories) {
        this.stories = stories;
    }

    public RealmList<ZhihuTop> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(RealmList<ZhihuTop> top_stories) {
        this.top_stories = top_stories;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }


}
