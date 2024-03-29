package com.shixun.ihome.publicservice.pojo;

import java.util.ArrayList;
import java.util.List;

public class IOrderNewsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public IOrderNewsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNull() {
            addCriterion("order_id is null");
            return (Criteria) this;
        }

        public Criteria andOrderIdIsNotNull() {
            addCriterion("order_id is not null");
            return (Criteria) this;
        }

        public Criteria andOrderIdEqualTo(Integer value) {
            addCriterion("order_id =", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotEqualTo(Integer value) {
            addCriterion("order_id <>", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThan(Integer value) {
            addCriterion("order_id >", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("order_id >=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThan(Integer value) {
            addCriterion("order_id <", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdLessThanOrEqualTo(Integer value) {
            addCriterion("order_id <=", value, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdIn(List<Integer> values) {
            addCriterion("order_id in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotIn(List<Integer> values) {
            addCriterion("order_id not in", values, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdBetween(Integer value1, Integer value2) {
            addCriterion("order_id between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andOrderIdNotBetween(Integer value1, Integer value2) {
            addCriterion("order_id not between", value1, value2, "orderId");
            return (Criteria) this;
        }

        public Criteria andODescribeIsNull() {
            addCriterion("o_describe is null");
            return (Criteria) this;
        }

        public Criteria andODescribeIsNotNull() {
            addCriterion("o_describe is not null");
            return (Criteria) this;
        }

        public Criteria andODescribeEqualTo(String value) {
            addCriterion("o_describe =", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeNotEqualTo(String value) {
            addCriterion("o_describe <>", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeGreaterThan(String value) {
            addCriterion("o_describe >", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeGreaterThanOrEqualTo(String value) {
            addCriterion("o_describe >=", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeLessThan(String value) {
            addCriterion("o_describe <", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeLessThanOrEqualTo(String value) {
            addCriterion("o_describe <=", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeLike(String value) {
            addCriterion("o_describe like", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeNotLike(String value) {
            addCriterion("o_describe not like", value, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeIn(List<String> values) {
            addCriterion("o_describe in", values, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeNotIn(List<String> values) {
            addCriterion("o_describe not in", values, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeBetween(String value1, String value2) {
            addCriterion("o_describe between", value1, value2, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andODescribeNotBetween(String value1, String value2) {
            addCriterion("o_describe not between", value1, value2, "oDescribe");
            return (Criteria) this;
        }

        public Criteria andSolveIsNull() {
            addCriterion("solve is null");
            return (Criteria) this;
        }

        public Criteria andSolveIsNotNull() {
            addCriterion("solve is not null");
            return (Criteria) this;
        }

        public Criteria andSolveEqualTo(String value) {
            addCriterion("solve =", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotEqualTo(String value) {
            addCriterion("solve <>", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveGreaterThan(String value) {
            addCriterion("solve >", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveGreaterThanOrEqualTo(String value) {
            addCriterion("solve >=", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveLessThan(String value) {
            addCriterion("solve <", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveLessThanOrEqualTo(String value) {
            addCriterion("solve <=", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveLike(String value) {
            addCriterion("solve like", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotLike(String value) {
            addCriterion("solve not like", value, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveIn(List<String> values) {
            addCriterion("solve in", values, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotIn(List<String> values) {
            addCriterion("solve not in", values, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveBetween(String value1, String value2) {
            addCriterion("solve between", value1, value2, "solve");
            return (Criteria) this;
        }

        public Criteria andSolveNotBetween(String value1, String value2) {
            addCriterion("solve not between", value1, value2, "solve");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}