
package org.jiumao.parse;

import java.util.List;

@FunctionalInterface
public interface Format<R>{
    R format(String source,List<String> params);
}
