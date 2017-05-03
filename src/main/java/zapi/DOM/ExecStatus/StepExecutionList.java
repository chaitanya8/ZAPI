package zapi.DOM.ExecStatus;

/**
 * Created by Chaitanya on 4-Jul-15.
 */
public class StepExecutionList {
    ExecutionStatusDOM[] list;

    public ExecutionStatusDOM[] getList() {
        return list;
    }

    public void setList(ExecutionStatusDOM[] list) {
        this.list = list;
    }

    public int getId(String name){
        ExecutionStatusDOM statusDOM = null;
        for (ExecutionStatusDOM x : this.getList()){
            if (x.getName().equals(name)){
                statusDOM = x;
            }
        }
        return statusDOM.getId();
    }
}
