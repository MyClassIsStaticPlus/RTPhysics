package ru.b7.rtphysics.ScreenElements;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ru.b7.rtphysics.BaseActivity;
import ru.b7.rtphysics.Database.Access_API.Finder;

/**
 * Created by Nikita on 12.11.2015.
 */

public class MenuParagraph extends StyleGlobal {

    TagSetter tag;
    String content;



    public MenuParagraph(BaseActivity activity, TagSetter tag) {
        super(activity, "Formula");

        this.tag = tag;
        content = Finder.GetByID("Articles", tag.id).get("Paragraph");

    }

    public MenuParagraph(BaseActivity activity,String content){
        super(activity,null);
        this.content=content;
    }

    @Override
    public View buildMainLayout() {

        ScrollView scroll = new ScrollView(parentContext);
        LinearLayout lay = super.buildWidgetsOnLay(LayoutCreate(content));
        scroll.addView(lay);

        return scroll;
    }

    private List<View> LayoutCreate(String content) {

        TextView paragraphPage = new TextView(super.parentContext);

        paragraphPage.setPadding(0, 20, 0, 0);
        paragraphPage.setText(Html.fromHtml(content, new MyImageGetter(parentContext), null));

        List<View> elements = new ArrayList<>();
        elements.add(paragraphPage);

        return elements;
    }


    public class MyImageGetter implements Html.ImageGetter {

        Context c;
        public MyImageGetter(Context c)
        {
            this.c = c;
        }

        @Override
        public Drawable getDrawable(String source) {

            Drawable drawFromPath;
            int path = c.getResources().getIdentifier(source, "drawable", c.getPackageName());
            drawFromPath = c.getResources().getDrawable(path);
            drawFromPath.setBounds(0, 0, drawFromPath.getIntrinsicWidth(),
                    drawFromPath.getIntrinsicHeight());

            return drawFromPath;
        }
    }

}
