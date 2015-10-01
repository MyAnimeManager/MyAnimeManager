package util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.AbstractListModel;

public class SortedListModel extends AbstractListModel {
  SortedSet<String> model;

  public SortedListModel() {
    model = new TreeSet<String>(String.CASE_INSENSITIVE_ORDER);
  }

  public int getSize() {
    return model.size();
  }

  public Object getElementAt(int index) {
    return model.toArray()[index];
  }

  public void addElement(String element) {
    if (model.add(element)) {
      fireContentsChanged(this, 0, getSize());
  }
}
  public void addAll(String elements[]) {
    Collection<String> c = Arrays.asList(elements);
    model.addAll(c);
    fireContentsChanged(this, 0, getSize());
  }

  public void clear() {
    model.clear();
    fireContentsChanged(this, 0, getSize());
  }

  public boolean contains(Object element) {
    return model.contains(element);
  }

  public Object firstElement() {
    return model.first();
  }

  public Iterator iterator() {
    return model.iterator();
  }

  public Object lastElement() {
    return model.last();
  }

  public boolean removeElement(Object element) {
    boolean removed = model.remove(element);
    if (removed) {
      fireContentsChanged(this, 0, getSize());
    }
    return removed;
  }

public boolean isEmpty()
{
	if (this.getSize() == 0)
		return true;
	else
		return false;
}

public Object[] toArray()
{
	Object[] array = model.toArray();
	return array;
}

public boolean removeElementAt(int index)
{
	Object object = getElementAt(index); 
	boolean removed = model.remove(object);
	    if (removed) {
	      fireContentsChanged(this, 0, getSize());
	    }
	    return removed;	
}

}