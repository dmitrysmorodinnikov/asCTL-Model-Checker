package helpers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.State;

public class CollectionHelper {

	public <T> Set<T> union(Set<T> list1, Set<T> list2) {
        Set<T> set = new HashSet<T>();

        set.addAll(list1);
        set.addAll(list2);

        return new HashSet<T>(set);
    }

    public <T> Set<T> intersection(Set<T> list1, Set<T> list2) {
        Set<T> list = new HashSet<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    public <T> List<T> intersection(List<T> list1, List<T> list2) {
        List<T> list = new ArrayList<T>();

        for (T t : list1) {
            if(list2.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }
    
    /**
     * Checks if at least one of the elements in array belongs to the set
     * @param set
     * @param array
     * @return
     */
    public <T> boolean contains(T[] array, Set<T> set){
    	for (T key : array){
    		if (set.contains(key)) return true;
    	}
    	return false;
    }
    
//    public Set<State> intersection(List<String> asList, List<String> actions) {
//		// TODO Auto-generated method stub
//		return null;
//	}
    
    public <T> Set<T> substraction(Set<T> list1, Set<T> list2) {
        Set<T> set = new HashSet<T>();  
        set.addAll(list1);
        set.removeAll(list2);
        
        return set;
    }
    
    public Set<State> cloneSet(Set<State> set) {
        Set<State> clone = new HashSet<State>(set.size());
        for (State item : set)
			try {
				clone.add((State) item.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        return clone;
    }

	
}
