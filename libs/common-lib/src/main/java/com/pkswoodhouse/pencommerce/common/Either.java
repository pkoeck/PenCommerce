package com.pkswoodhouse.pencommerce.common;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Either<L, R> {
    
    private Either() {}
    
    public static <L, R> Either<L, R> left(L value) {
        return new Left<>(value);
    }

    public static <L, R> Either<L, R> left(Supplier<L> supplier) { return new Left<>(supplier.get()); }

    public static <L, R> Either<L, R> right(R value) {
        return new Right<>(value);
    }
    public static <L, R> Either<L, R> right(Supplier<R> supplier) { return new Right<>(supplier.get()); }
    
    public abstract boolean isLeft();
    public abstract boolean isRight();
    public abstract Optional<L> left();
    public abstract Optional<R> right();
    
    public abstract <T> Either<L, T> mapRight(Function<R, T> mapper);
    public abstract <T> Either<T, R> mapLeft(Function<L, T> mapper);
    
    public abstract <T> Either<L, T> flatMapRight(Function<R, Either<L, T>> mapper);
    public abstract <T> Either<T, R> flatMapLeft(Function<L, Either<T, R>> mapper);
    
    private static class Left<L, R> extends Either<L, R> {
        private final L value;
        
        private Left(L value) {
            this.value = value;
        }
        
        @Override
        public boolean isLeft() {
            return true;
        }
        
        @Override
        public boolean isRight() {
            return false;
        }
        
        @Override
        public Optional<L> left() {
            return Optional.of(value);
        }
        
        @Override
        public Optional<R> right() {
            return Optional.empty();
        }
        
        @Override
        public <T> Either<L, T> mapRight(Function<R, T> mapper) {
            return Either.left(value);
        }
        
        @Override
        public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
            return Either.left(mapper.apply(value));
        }
        
        @Override
        public <T> Either<L, T> flatMapRight(Function<R, Either<L, T>> mapper) {
            return Either.left(value);
        }
        
        @Override
        public <T> Either<T, R> flatMapLeft(Function<L, Either<T, R>> mapper) {
            return mapper.apply(value);
        }
    }
    
    private static class Right<L, R> extends Either<L, R> {
        private final R value;
        
        private Right(R value) {
            this.value = value;
        }
        
        @Override
        public boolean isLeft() {
            return false;
        }
        
        @Override
        public boolean isRight() {
            return true;
        }
        
        @Override
        public Optional<L> left() {
            return Optional.empty();
        }
        
        @Override
        public Optional<R> right() {
            return Optional.of(value);
        }
        
        @Override
        public <T> Either<L, T> mapRight(Function<R, T> mapper) {
            return Either.right(mapper.apply(value));
        }
        
        @Override
        public <T> Either<T, R> mapLeft(Function<L, T> mapper) {
            return Either.right(value);
        }
        
        @Override
        public <T> Either<L, T> flatMapRight(Function<R, Either<L, T>> mapper) {
            return mapper.apply(value);
        }
        
        @Override
        public <T> Either<T, R> flatMapLeft(Function<L, Either<T, R>> mapper) {
            return Either.right(value);
        }
    }
    
    public static <L, R> Either<L, R> fromOptional(Optional<R> optional, L leftValue) {
        return optional.map(Either::<L, R>right).orElseGet(() -> Either.left(leftValue));
    }
    
    public <T> T fold(Function<L, T> leftMapper, Function<R, T> rightMapper) {
        if (this.isLeft()) {
            return leftMapper.apply(this.left().orElseThrow());
        } else {
            return rightMapper.apply(this.right().orElseThrow());
        }
    }
}
