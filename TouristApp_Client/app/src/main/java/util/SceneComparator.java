package util;

import java.util.Comparator;

import model.Scene;

/**
 * Created by vincent on 15/4/9.
 */
/*Compare the scenes made in the scheduler*/
public class SceneComparator implements Comparator<Scene>
{
    public int compare(Scene c1, Scene c2)
    {
        if(c1.getDate()!=c2.getDate()) {
            return c1.getDate() - c2.getDate();
        }
        else {
            return c1.getTime() - c2.getTime();
        }
    }
}
