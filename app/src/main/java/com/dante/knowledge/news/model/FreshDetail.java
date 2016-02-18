package com.dante.knowledge.news.model;

import com.dante.knowledge.news.other.NewsDetail;

import io.realm.RealmObject;

/**
 * fresh things detail class
 */
public class FreshDetail extends RealmObject implements NewsDetail{

    /**
     * status : ok
     * post : {"id":74732,"content":"<p><img src=\"http://tankr.net/s/medium/O5XD.jpg\" alt=\"英语：一门积极情感匮乏的语言\" /><\/p>\n<p>一位英国研究者最近在研究跟积极情绪和概念相关的非英语词汇时，发现有216个外语单词，没有对应的英语翻译。<\/p>\n<p>目前，科学家们正在讨论语言及其跟意识经验的关系。哲学家和认知科学家Daniel Dennett曾说过语言\u201c可以在各个级别影响和改变我们的想法\u201d，认为我们对世界认知的很大一部分都受我们使用的词汇的影响。在另一方面，很多心理学家则很难相信，人类会因为缺乏相应词汇而难以理解某种概念或者某种情绪。但是正如东伦敦大学心理学家Tim Lomas在最新研究中指出的那样，不断扩大的词汇量可以潜在\u201c丰富我们对幸福的体验。\u201d<\/p>\n<p>他的研究发表在《积极心理学》杂志上，其中列举了多达216个表达积极情绪却\u201c无法被翻译的单词\u201d\u2014\u2014即没有对应的英文单词。Lomas这么做是因为他希望了解其他文化是如何同语言表达积极情绪的概念，而且更大的目标是，他希望这些单词能让丰富英语使用者的幸福体验。<\/p>\n<p>Lomas把这些单词分成3类，分别举例如下：<\/p>\n<h4>跟感情有关的单词：<\/h4>\n<h4 class=\"pullquote\">· Gula: 西班牙语单词，只为食物本身味道而吃的欲望。<br />\n· Sobremesa: 西班牙单词，酒足饭饱，但是交谈仍然在继续。<br />\n· Mbukimvuki: 班图语，\u201c为了跳舞而剥去某人的衣服\u201d。<br />\n· Schnapsidee: 德语，在喝醉后脑洞大开。<br />\n· Volta: 希腊语，悠闲地轧马路。<\/h4>\n<h4>跟人际关系有关的单词：<\/h4>\n<h4 class=\"pullquote\">· Nakama: 日语，当做家人一般的朋友。<br />\n· Kanyininpa: 宾图比语，扶持与被扶持的关系，类似于父母对子女那种深厚的养育之情。<br />\n· Gigil: 塔加拉族语，因为深爱某人而忍不住要捏ta或者掐ta的冲动。<br />\n· Kilig: 塔加拉族语，在跟你喜欢的人一起时，内心小鹿乱撞(原文：胃中有蝴蝶飞舞)的感觉<br />\n· Sarang: 韩语，想要跟某人到死也不分开的愿望<\/h4>\n<h4>跟性格有关的单词：<\/h4>\n<h4 class=\"pullquote\">· Sitzfleisch: 德语，在艰难或者枯燥的任务中坚持不懈的能力(字面意思，坐着的一团肉)。<br />\n· Baraka: 阿拉伯语，一种能够将精神能量从一个人传给另一个人的天赋。<br />\n· Jugaad: 印地语，会凑合的能力。<br />\n· Desenrascanco: 葡萄牙语，巧妙地从棘手情形中脱身的能力。<br />\n· Sprezzatura: 意大利语，把技艺和努力都掩盖在\u201c故意显露的粗心大意中\u201d。<\/h4>\n<p>你可以在<a target=_blank rel=\"external\" href=\"http://digest.bps.org.uk/2016/01/there-are-at-least-216-foreign-words.html\">BPS Digest<\/a>中找到更多的更多的例子。Lomas承认他的216个单词只是九牛一毛，他将继续补充这个列表，在<a target=_blank rel=\"external\" href=\"http://www.drtimlomas.com/#!lexicography/cm4mi\">这个网站<\/a>大家可以找到，而且他鼓励大家提供意见。<\/p>\n<p>当然，相比这个研究，其实还应该总结一个没有英语翻译的负面情绪单词清单。这样我们才能知道英语是否如同人们所见那样是一种神经质语言。<\/p>\n<p><em>#好像其中不少单词也找不到对应的汉语呢<\/em><\/p>\n<p><em>[<a href=\"http://jandan.net/2016/02/01/english-positive-emotion.html\">Cedric<\/a> via <a target=_blank rel=\"external\" href=\"http://gizmodo.com/english-is-surprisingly-devoid-of-emotionally-positive-1755985162\">gizmodo<\/a>]<\/em><\/p>\n<div class=\"share-links\">\n        <a href=\"http://service.weibo.com/share/share.php?appkey=43259970&searchPic=true&url=http%3A%2F%2Fjandan.net%2F2016%2F02%2F01%2Fenglish-positive-emotion.html&title=%E3%80%90%E8%8B%B1%E8%AF%AD%EF%BC%9A%E4%B8%80%E9%97%A8%E7%A7%AF%E6%9E%81%E6%83%85%E6%84%9F%E5%8C%AE%E4%B9%8F%E7%9A%84%E8%AF%AD%E8%A8%80%E3%80%91%E4%B8%80%E4%BD%8D%E8%8B%B1%E5%9B%BD%E7%A0%94%E7%A9%B6%E8%80%85%E6%9C%80%E8%BF%91%E5%9C%A8%E7%A0%94%E7%A9%B6%E8%B7%9F%E7%A7%AF%E6%9E%81%E6%83%85%E7%BB%AA%E5%92%8C%E6%A6%82%E5%BF%B5%E7%9B%B8%E5%85%B3%E7%9A%84%E9%9D%9E%E8%8B%B1%E8%AF%AD%E8%AF%8D%E6%B1%87%E6%97%B6%EF%BC%8C%E5%8F%91%E7%8E%B0%E6%9C%89216%E4%B8%AA%E5%A4%96%E8%AF%AD%E5%8D%95%E8%AF%8D%EF%BC%8C%E6%B2%A1%E6%9C%89%E5%AF%B9%E5%BA%94%E7%9A%84%E8%8B%B1%E8%AF%AD%E7%BF%BB%E8%AF%91%40%E7%85%8E%E8%9B%8B%E7%BD%91&pic=http%3A%2F%2Fjandan.net%2Fshare%2F2016%2F02%2Fp-74732.gif&style=number&language=zh_cn&button=pubilish\" class=\"share-link share-link-weibo\" target=\"_blank\">分享到微博<\/a>\n            <a href=\"javascript:;\" class=\"share-link share-link-weixin\"><img src=\"http://jandan.net/share/2016/02/qr-74732.png\">分享到微信<\/a>\n    <\/div>\n"}
     * previous_url : http://jandan.net/2016/02/01/liver-tissue.html
     * next_url : http://jandan.net/2016/02/01/fruits-before-domesticated.html
     */

    private String status;
    /**
     * id : 74732
     * content : <p><img src="http://tankr.net/s/medium/O5XD.jpg" alt="英语：一门积极情感匮乏的语言" /></p>
     * blah blah
     </div>
     */

    private PostEntity post;
    private String previous_url;
    private String next_url;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPost(PostEntity post) {
        this.post = post;
    }

    public void setPrevious_url(String previous_url) {
        this.previous_url = previous_url;
    }

    public void setNext_url(String next_url) {
        this.next_url = next_url;
    }

    public String getStatus() {
        return status;
    }

    public PostEntity getPost() {
        return post;
    }

    public String getPrevious_url() {
        return previous_url;
    }

    public String getNext_url() {
        return next_url;
    }


}
